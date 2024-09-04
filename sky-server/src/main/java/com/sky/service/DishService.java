package com.sky.service;

import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

public interface DishService {
    PageResult page(DishPageQueryDTO dto);

    void updateStatus(Integer status, Long id);
}
