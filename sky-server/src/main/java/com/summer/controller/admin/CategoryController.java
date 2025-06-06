package com.summer.controller.admin;

import com.summer.dto.CategoryDTO;
import com.summer.dto.CategoryPageQueryDTO;
import com.summer.entity.Category;
import com.summer.result.PageResult;
import com.summer.result.Result;
import com.summer.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Api(tags = "分类管理")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @PostMapping
    @ApiOperation("新增")
    public Result<String> save(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类: {}", categoryDTO);

        categoryService.save(categoryDTO);

        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("列表")
    public Result<PageResult<Category>> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分页查询: {}", categoryPageQueryDTO);

        PageResult<Category> pageResult = categoryService.pageQuery(categoryPageQueryDTO);

        return Result.success(pageResult);
    }

    @PutMapping
    @ApiOperation("修改")
    public Result<Object> update(@RequestBody CategoryDTO categoryDTO) {
        log.info("编辑分类: {}", categoryDTO);

        categoryService.update(categoryDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除")
    public Result<Object> delete(@PathVariable Long id) {
        log.info("删除分类: {}", id);

        categoryService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用")
    public Result<Void> startOrStop(@PathVariable("status") Integer status, Long id) {
        log.info("启用或禁用分类: {}, {}", status, id);

        categoryService.startOrStop(status, id);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("按类型查询")
    public Result<List<Category>> list(Integer type) {
        List<Category> categories = categoryService.list(type);

        return Result.success(categories);
    }
}
