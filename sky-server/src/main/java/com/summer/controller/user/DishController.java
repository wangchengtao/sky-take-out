package com.summer.controller.user;

import com.summer.constant.StatusConstant;
import com.summer.entity.Dish;
import com.summer.result.Result;
import com.summer.service.DishService;
import com.summer.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("user/dish")
@Slf4j
@Api(tags = "用户-菜品管理")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/list")
    @ApiOperation("根据分类 id 查询菜品")
    public Result<List<DishVO>> list(Long categoryId) {

        String key = "dish_" + categoryId;

        List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);

        if (list != null && !list.isEmpty()) {
            return Result.success(list);
        }

        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);

        list = dishService.listWithFlavor(dish);

        redisTemplate.opsForValue().set(key, list);

        return Result.success(list);
    }
}
