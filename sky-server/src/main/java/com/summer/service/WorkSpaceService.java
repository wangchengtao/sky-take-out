package com.summer.service;

import com.summer.vo.BusinessDataVO;
import com.summer.vo.DishOverViewVO;
import com.summer.vo.OrderOverViewVO;
import com.summer.vo.SetmealOverViewVO;

import java.time.LocalDateTime;

public interface WorkSpaceService {
    BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);

    OrderOverViewVO getOrderOverView();

    DishOverViewVO getDishOverView();

    SetmealOverViewVO getSetmealOverView();
}
