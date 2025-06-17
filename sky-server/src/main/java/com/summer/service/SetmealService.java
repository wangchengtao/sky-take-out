package com.summer.service;

import com.summer.dto.SetmealDTO;
import com.summer.entity.Setmeal;
import com.summer.vo.DishItemVO;

import java.util.List;

public interface SetmealService {

    List<Setmeal> list(Setmeal setmeal);

    List<DishItemVO> getDishItemById(Long id);

    void saveWithDish(SetmealDTO setmealDTO);

    void deleteBatch(List<Long> ids);

    void startOrStop(Integer status, Long id);

    void updateWithDish(SetmealDTO setmealDTO);
}
