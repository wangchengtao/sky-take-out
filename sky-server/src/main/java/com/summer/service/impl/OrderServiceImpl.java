package com.summer.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.summer.constant.MessageConstant;
import com.summer.context.BaseContext;
import com.summer.dto.OrderPaymentDTO;
import com.summer.dto.OrderSubmitDTO;
import com.summer.entity.*;
import com.summer.execption.AddressBookBusinessException;
import com.summer.execption.OrderBusinessException;
import com.summer.execption.ShoppingCartBusinessException;
import com.summer.mapper.*;
import com.summer.service.OrderService;
import com.summer.utils.WeChatPayUtil;
import com.summer.vo.OrderPaymentVO;
import com.summer.vo.OrderSubmitVO;
import com.summer.websocket.WebSocketServer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WeChatPayUtil weChatPayUtil;

    @Autowired
    private WebSocketServer webSocketServer;

    @Override
    @Transactional
    public OrderSubmitVO submitOrder(OrderSubmitDTO orderSubmitDTO) {
        AddressBook book = addressBookMapper.getById(orderSubmitDTO.getAddressBookId());

        if (book == null) {
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }

        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);

        List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);

        if (shoppingCartList == null || shoppingCartList.isEmpty()) {
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }

        Orders order = new Orders();
        BeanUtils.copyProperties(orderSubmitDTO, order);
        order.setPhone(book.getPhone());
        order.setAddress(book.getDetail());
        order.setConsignee(book.getConsignee());
        order.setNumber(String.valueOf(System.currentTimeMillis()));
        order.setUserId(userId);
        order.setStatus(Orders.PENDING_PAYMENT);
        order.setPayStatus(Orders.UN_PAID);
        order.setOrderTime(LocalDateTime.now());

        orderMapper.insert(order);

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (ShoppingCart cart : shoppingCartList) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(order.getId());
            orderDetails.add(orderDetail);
        }

        orderDetailMapper.insertBatch(orderDetails);

        shoppingCartMapper.deleteByUserId(userId);

        return OrderSubmitVO.builder()
                .id(order.getId())
                .orderNumber(order.getNumber())
                .orderAmount(order.getAmount())
                .orderTime(order.getOrderTime())
                .build();
    }

    @Override
    public OrderPaymentVO payment(OrderPaymentDTO orderPaymentDTO) throws Exception {
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.getById(userId);

        JSONObject jsonObject = weChatPayUtil.pay(orderPaymentDTO.getOrderNumber(), new BigDecimal("0.01"), "苍穹外卖订单", user.getOpenid());

        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
            throw new OrderBusinessException("该订单已支付");
        }

        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
        vo.setPackageStr(jsonObject.getString("package"));

        return vo;
    }

    /**
     * 支付成功, 修改订单状态
     */
    @Override
    public void paySuccess(String outTradeNo) {
        Long userId = BaseContext.getCurrentId();

        Orders orderDB = orderMapper.getByNumberAndUserId(outTradeNo, userId);

        Orders orders = Orders.builder()
                .id(orderDB.getId())
                .status(Orders.TO_BE_CONFIRMED)
                .payStatus(Orders.PAID)
                .checkoutTime(LocalDateTime.now())
                .build();

        orderMapper.update(orders);

        HashMap<String, Object> map = new HashMap<>();
        map.put("type", 1);
        map.put("orderId", orders.getId());
        map.put("content", "订单号：" + outTradeNo);

        webSocketServer.sendToAllClient(JSONObject.toJSONString(map));
    }


}
