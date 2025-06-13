package com.summer.controller.admin;

import com.summer.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "后台-店铺管理")
@Slf4j
public class ShopController {

    public static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PutMapping("/{status}")
    @ApiOperation("设置店铺营业状态")
    public Result<Void> setStatus(@PathVariable Integer status) {
        log.info("设置店铺营业状态：{}", status == 1 ? "营业中" : "打烊中");

        redisTemplate.opsForValue().set(KEY, status.toString());

        return Result.success();

    }

    @GetMapping("/status")
    @ApiOperation("获取店铺营业状态")
    public Result<String> getStatus() {
        log.info("获取店铺营业状态");

        String s = redisTemplate.opsForValue().get(KEY);

        return Result.success(s);
    }
}
