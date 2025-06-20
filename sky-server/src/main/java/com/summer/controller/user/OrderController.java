package com.summer.controller.user;

import com.summer.dto.OrderPaymentDTO;
import com.summer.dto.OrderSubmitDTO;
import com.summer.result.Result;
import com.summer.service.OrderService;
import com.summer.vo.OrderPaymentVO;
import com.summer.vo.OrderSubmitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userOrderController")
@RequestMapping("user/order")
@Api(tags = "用户-订单")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("submit")
    @ApiOperation("用户下单")
    public Result<OrderSubmitVO> submit(@RequestBody OrderSubmitDTO orderSubmitDTO) {
        log.info("用户下单: {}", orderSubmitDTO);

        OrderSubmitVO orderSubmitVO = orderService.submitOrder(orderSubmitDTO);

        return Result.success(orderSubmitVO);
    }

    @PostMapping("payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrderPaymentDTO orderPaymentDTO) throws Exception {
        log.info("订单支付: {}", orderPaymentDTO);

        OrderPaymentVO vo = orderService.payment(orderPaymentDTO);
        log.info("支付结果: {}", vo);

        return Result.success(vo);
    }

    @GetMapping("reminder/{id}")
    @ApiOperation("催单")
    public Result<Void> reminder(@PathVariable long id) {
        orderService.reminder(id);

        return Result.success();
    }

}
