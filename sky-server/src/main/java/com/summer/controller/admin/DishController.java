package com.summer.controller.admin;

import com.summer.dto.DishDTO;
import com.summer.dto.DishPageQueryDTO;
import com.summer.result.PageResult;
import com.summer.result.Result;
import com.summer.service.DishService;
import com.summer.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminDishController")
@RequestMapping("/admin/dish")
@Api(tags = "菜品管理")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping
    @ApiOperation(value = "新增菜品", produces = "application/json")
    @CacheEvict(cacheNames = "dishCache", key = "#dishDTO.categoryId")
    public Result<Void> save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品: {}", dishDTO);

        dishService.saveWithFlavor(dishDTO);

        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("列表")
    public Result<PageResult<DishVO>> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("分页查询: {}", dishPageQueryDTO);

        PageResult<DishVO> pageResult = dishService.pageQuery(dishPageQueryDTO);

        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("批量删除")
    @CacheEvict(cacheNames = "dishCache", allEntries = true)
    public Result<Void> delete(@RequestParam List<Long> ids) {
        log.info("批量删除: {}", ids);

        dishService.deleteBatch(ids);

        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("详情")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("根据id查询: {}", id);

        DishVO dishVO = dishService.getByIdWithFlavor(id);

        return Result.success(dishVO);
    }

    @PutMapping
    @ApiOperation("编辑")
    @CacheEvict(cacheNames = "dishCache", key = "#dishDTO.categoryId")
    public Result<Void> update(@RequestBody DishDTO dishDTO) {
        log.info("编辑菜品信息：{}", dishDTO);

        dishService.updateWithFlavor(dishDTO);

        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("起售停售")
    @CacheEvict(cacheNames = "dishCache", allEntries = true)
    public Result<String> startOrStop(@PathVariable("status") Integer status, Long id) {
        dishService.startOrStop(status, id);

        return Result.success();
    }
}
