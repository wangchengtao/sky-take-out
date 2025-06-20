package com.summer.service;

import com.summer.dto.OrderPaymentDTO;
import com.summer.dto.OrderSubmitDTO;
import com.summer.vo.OrderPaymentVO;
import com.summer.vo.OrderSubmitVO;

public interface OrderService {
    OrderSubmitVO submitOrder(OrderSubmitDTO orderSubmitDTO);

    OrderPaymentVO payment(OrderPaymentDTO orderPaymentDTO) throws Exception;

    void paySuccess(String outTradeNo);

    void reminder(long id);
}
