package com.summer.service;

import com.summer.dto.CategoryDTO;
import com.summer.dto.CategoryPageQueryDTO;
import com.summer.entity.Category;
import com.summer.result.PageResult;

import java.util.List;

public interface CategoryService {

    void save(CategoryDTO categoryDTO);

    PageResult<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void deleteById(Long id);

    void update(CategoryDTO categoryDTO);

    void startOrStop(Integer status, Long id);

    Category getById(Long id);

    List<Category> list(Integer type);
}
