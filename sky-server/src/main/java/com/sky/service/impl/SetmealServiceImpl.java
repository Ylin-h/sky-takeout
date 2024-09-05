package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
   @Autowired
   private SetmealMapper setmealMapper;
   @Autowired
   private SetmealDishMapper setmealDishMapper;
   @Autowired
   private DishMapper dishMapper;

   /**
     * 分页查询套餐信息
     * @param setmealPageQueryDTO
     * @return
     */
    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        int pageNum = setmealPageQueryDTO.getPage();
        int pageSize = setmealPageQueryDTO.getPageSize();

        PageHelper.startPage(pageNum, pageSize);
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    //新增套餐
   @Transactional
    public void saveWithDish(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        //套餐表插入数据
        setmealMapper.insert(setmeal);
       //获取到套餐id
        Long setmealId = setmeal.getId();
        //遍历菜品id，插入套餐菜品中间表
       List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
       setmealDishes.forEach(setmealDish -> {
           setmealDish.setSetmealId(setmealId);
       });
       //保存套餐和菜品的关联关系
       setmealDishMapper.insertBatch(setmealDishes);
    }
}
