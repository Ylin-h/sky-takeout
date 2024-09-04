package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishPageQueryDTO;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    //分页查询菜品
    public PageResult page(DishPageQueryDTO dto) {
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        Page<DishVO> page = dishMapper.page(dto);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void updateStatus(Integer status, Long id) {
        //更新菜品状态
        //不想用bulider设置更新时间，用户了，sql太多字段属性-_-
        dishMapper.updateStatus(status, id);
    }
}
