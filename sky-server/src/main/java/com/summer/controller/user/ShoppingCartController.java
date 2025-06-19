package com.summer.controller.user;

import com.summer.dto.ShoppingCartDTO;
import com.summer.entity.ShoppingCart;
import com.summer.result.Result;
import com.summer.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user/shoppingCart")
@Api(tags = "用户-购物车")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("add")
    @ApiOperation("添加购物车")
    public Result<Void> add(@RequestBody ShoppingCartDTO shoppingCartDTO) {

        log.info("添加购物车: {}", shoppingCartDTO);

        shoppingCartService.addShoppingCart(shoppingCartDTO);

        return Result.success();
    }

    @GetMapping("list")
    @ApiOperation("购物车列表")
    public Result<List<ShoppingCart>> list() {
        return Result.success(shoppingCartService.showShoppingCart());
    }

    @DeleteMapping("/clean")
    @ApiOperation("清空购物车")
    public Result<Void> clean() {
        shoppingCartService.cleanShoppingCart();

        return Result.success();
    }
}
