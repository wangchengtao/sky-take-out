package com.summer.mapper;

import com.github.pagehelper.Page;
import com.summer.dto.CategoryPageQueryDTO;
import com.summer.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Insert("insert into category (type, name, sort, status, create_time, update_time, create_user, update_user) values (#{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insert(Category category);

    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    @Select("select * from category where id = #{id}")
    Category getById(Long id);

    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);

    void update(Category category);

    List<Category> list(Integer type);
}
