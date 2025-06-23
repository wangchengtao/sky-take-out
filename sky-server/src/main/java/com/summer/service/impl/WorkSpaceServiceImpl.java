package com.summer.service.impl;

import com.summer.constant.StatusConstant;
import com.summer.entity.Orders;
import com.summer.mapper.DishMapper;
import com.summer.mapper.OrderMapper;
import com.summer.mapper.SetmealMapper;
import com.summer.mapper.UserMapper;
import com.summer.service.WorkSpaceService;
import com.summer.vo.BusinessDataVO;
import com.summer.vo.DishOverViewVO;
import com.summer.vo.OrderOverViewVO;
import com.summer.vo.SetmealOverViewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;

@Service
@Slf4j
public class WorkSpaceServiceImpl implements WorkSpaceService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("begin", begin);
        map.put("end", end);

        Integer totalOrderCount = orderMapper.countByMap(map);

        map.put("status", Orders.COMPLETED);

        Double turnover = orderMapper.sumByMap(map);
        turnover = turnover == null ? 0.0 : turnover;

        Integer validOrderCount = orderMapper.countByMap(map);

        Double unitPrice = 0.0;
        Double orderCompletionRate = 0.0;

        if (totalOrderCount != 0 && validOrderCount != 0) {
            unitPrice = turnover / validOrderCount;
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
        }

        Integer newUsers = userMapper.countByMap(map);

        return BusinessDataVO.builder()
                .turnover(turnover)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice)
                .newUsers(newUsers)
                .build();
    }

    @Override
    public OrderOverViewVO getOrderOverView() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("begin", LocalDateTime.now().with(LocalTime.MIN));

        map.put("status", Orders.TO_BE_CONFIRMED);
        Integer waitingOrders = orderMapper.countByMap(map);

        map.put("status", Orders.CONFIRMED);
        Integer deliveredOrders = orderMapper.countByMap(map);

        map.put("status", Orders.COMPLETED);
        Integer completedOrders = orderMapper.countByMap(map);

        map.put("status", Orders.CANCELLED);
        Integer canceledOrders = orderMapper.countByMap(map);

        map.put("status", null);
        Integer allOrders = orderMapper.countByMap(map);

        return OrderOverViewVO.builder()
                .waitingOrders(waitingOrders)
                .deliveredOrders(deliveredOrders)
                .completedOrders(completedOrders)
                .cancelledOrders(canceledOrders)
                .allOrders(allOrders)
                .build();
    }

    @Override
    public DishOverViewVO getDishOverView() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", StatusConstant.ENABLE);

        Integer sold = dishMapper.countByMap(map);

        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = dishMapper.countByMap(map);

        return DishOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }

    @Override
    public SetmealOverViewVO getSetmealOverView() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("status", StatusConstant.ENABLE);
        Integer sold = setmealMapper.countByMap(map);

        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = setmealMapper.countByMap(map);

        return SetmealOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }
}
