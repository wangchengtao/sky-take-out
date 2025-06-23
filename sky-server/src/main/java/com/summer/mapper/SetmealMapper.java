package com.summer.mapper;

import com.summer.entity.Setmeal;
import com.summer.vo.DishItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

/**
 * 套餐
 */
@Mapper
public interface SetmealMapper {

    @Select("select count(*) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);

    List<Long> getSetmealIdsByDishIds(List<Long> ids);

    List<Setmeal> list(Setmeal setmeal);

    void insert(Setmeal setmeal);

    @Select("select * from setmeal where id = #{id}")
    Setmeal getById(Long id);

    void deleteBatch(List<Long> ids);

    void update(Setmeal setmeal);

    Integer countByMap(HashMap<String, Object> map);
}
