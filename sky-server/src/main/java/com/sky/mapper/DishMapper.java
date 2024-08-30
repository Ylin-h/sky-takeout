package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.DishPageQueryDTO;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper

public interface DishMapper {

    Page<DishVO> page(DishPageQueryDTO dto);
}
