package com.summer.service;

import com.summer.entity.Setmeal;
import com.summer.vo.DishItemVO;

import java.util.List;

public interface SetmealService {

    List<Setmeal> list(Setmeal setmeal);

    List<DishItemVO> getDishItemById(Long id);
}
