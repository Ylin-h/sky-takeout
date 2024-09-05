package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController("adminShopController")//声明控制器，防止与user中的shopcontroller重复
@RequestMapping("/admin/shop")
@Slf4j
@Api(tags = "后台-商铺管理")
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;
    @PutMapping("/{status}")
    public Result updateshopStatus(@PathVariable Integer status) {
        log.info("修改商铺状态，status={}", status==1? "营业中" : "休息中");
       ValueOperations valueOperations = redisTemplate.opsForValue();
       valueOperations.set("shopStatus", status);
       return Result.success();
    }
    @GetMapping("/status")
    public Result<Integer> getShopStatus() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
         Integer shopStatus = (Integer) valueOperations.get("shopStatus");
        log.info("修改商铺状态，status={}", shopStatus==1? "营业中" : "休息中");
        return Result.success(shopStatus);
    }
}
