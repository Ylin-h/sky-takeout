package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.redis.core.RedisTemplate;

@RestController("userShopController")//设置用户
@Slf4j
@RequestMapping("/user/shop")
@Api(tags = "用户-商城")
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;
    @GetMapping("/status")
    @ApiOperation(value = "获取商铺状态", notes = "获取商铺状态")
    public Result<Integer> getShopStatus() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Integer shopStatus = (Integer) valueOperations.get("shopStatus");
        log.info("修改商铺状态，status={}", shopStatus==1? "营业中" : "休息中");
        return Result.success(shopStatus);
    }
}
