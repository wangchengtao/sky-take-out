package com.summer.mapper;

import com.github.pagehelper.Page;
import com.summer.annotation.AutoFill;
import com.summer.dto.DishPageQueryDTO;
import com.summer.entity.Dish;
import com.summer.enumeration.OperationType;
import com.summer.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface DishMapper {

    @Select("select count(*) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    @AutoFill(OperationType.INSERT)
    void insert(Dish dish);

    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);

    @Delete("delete from dish where id = #{id}")
    void deleteById(Long id);

    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    List<Dish> list(Dish dish);

    Integer countByMap(HashMap<String, Object> map);
}
