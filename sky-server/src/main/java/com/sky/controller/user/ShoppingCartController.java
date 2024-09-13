package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user/shoppingCart")
@Slf4j
@Api(tags = "购物车")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @PostMapping("/add")
    @ApiOperation(value = "购物车列表", notes = "获取当前用户的购物车列表")
    public Result addShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("购物车列表：{}", shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }
  @GetMapping("/list")
  @ApiOperation(value = "购物车列表", notes = "获取当前用户的购物车列表")
  public Result<List<ShoppingCart>> listShoppingCart() {
        log.info("购物车列表");
        List<ShoppingCart> shoppingCartList = shoppingCartService.listShoppingCart();
        return Result.success(shoppingCartList);
  }
  @DeleteMapping("/clean")
  @ApiOperation(value = "删除购物车", notes = "删除购物车")
  public Result deleteShoppingCart() {
        log.info("删除购物车");
        shoppingCartService.cleanShoppingCart();
        return Result.success();
  }
}
