package com.sky.controller.admin;

import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "后台菜品管理")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;
    // 新增菜品
    // 查询菜品列表
    @GetMapping("/page")
    @ApiOperation(value = "分页查询菜品列表", notes = "分页查询菜品列表")
    public Result<PageResult> page(DishPageQueryDTO dto) {
        log.info("分页查询菜品列表：{}", dto);
        PageResult pageResult = dishService.page(dto);
        return Result.success(pageResult);
    }
    @PostMapping("/status/{status}")
    @ApiOperation("修改菜品状态")
    public Result updateStatus(@PathVariable("status") Integer status, Long id) {
        log.info("修改菜品状态：{}，id：{}", status, id);
        dishService.updateStatus(status, id);
        redisTemplate.delete("dish_*");// 清除缓存
        return Result.success();
    }
    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId){
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

}
