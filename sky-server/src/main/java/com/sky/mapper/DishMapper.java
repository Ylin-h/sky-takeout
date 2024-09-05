package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper

public interface DishMapper {

    Page<DishVO> page(DishPageQueryDTO dto);


    Integer countByCategoryId(Long id);
@Update("UPDATE dish SET status = #{status} WHERE id = #{id}")
    void updateStatus(Integer status, Long id);

    List<Dish> list(Dish dish);
}
