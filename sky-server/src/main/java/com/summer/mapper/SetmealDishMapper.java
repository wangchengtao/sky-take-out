package com.summer.mapper;

import com.summer.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 套餐
 */
@Mapper
public interface SetmealDishMapper {

    @Delete("delete from setmeal_dish where setmeal_id = #{id}")
    void deleteBySetmealId(Long id);

    void insertBatch(List<SetmealDish> setmealDishes);
}
