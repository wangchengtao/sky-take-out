package com.summer.service;

import com.summer.dto.DishDTO;
import com.summer.dto.DishPageQueryDTO;
import com.summer.entity.Dish;
import com.summer.result.PageResult;
import com.summer.vo.DishVO;

import java.util.List;

public interface DishService {
    void saveWithFlavor(DishDTO dishDTO);

    PageResult<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void deleteBatch(List<Long> ids);

    DishVO getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDTO dishDTO);

    List<DishVO> listWithFlavor(Dish dish);

    void startOrStop(Integer status, Long id);
}
