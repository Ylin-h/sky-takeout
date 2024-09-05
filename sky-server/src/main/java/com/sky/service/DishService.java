package com.sky.service;

import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;

import java.util.List;

public interface DishService {
    PageResult page(DishPageQueryDTO dto);

    void updateStatus(Integer status, Long id);

    List<Dish> list(Long categoryId);
}
