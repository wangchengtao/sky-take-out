package com.summer.controller.user;


import com.summer.entity.Category;
import com.summer.result.Result;
import com.summer.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userCategoryController")
@RequestMapping("user/category")
@Api(tags = "用户-分类管理")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    @ApiOperation("查询分类")
    public Result<List<Category>> list(Integer type) {

        List<Category> categories = categoryService.list(type);

        return Result.success(categories);
    }
}
