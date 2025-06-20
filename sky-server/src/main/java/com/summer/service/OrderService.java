package com.summer.service;

import com.summer.dto.OrderSubmitDTO;
import com.summer.vo.OrderSubmitVO;

public interface OrderService {
    OrderSubmitVO submitOrder(OrderSubmitDTO orderSubmitDTO);
}
