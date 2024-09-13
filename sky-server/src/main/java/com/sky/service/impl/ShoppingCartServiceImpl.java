package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart cart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, cart);
        cart.setUserId(BaseContext.getCurrentId());
    //判断当前商品是否已经在购物车中
        List<ShoppingCart> list = shoppingCartMapper.list(cart);
    //如果已经在购物车中，则数量加1
        if(list!= null && list.size() > 0)
        {
            ShoppingCart cart1 = list.get(0);
            cart1.setNumber(cart1.getNumber() +1);
            shoppingCartMapper.update(cart1);
        }
    //如果不在购物车中，则添加到购物车中
        else{
            if(shoppingCartDTO.getDishId()!= null)
            {
                Dish dish= dishMapper.getById(shoppingCartDTO.getDishId());
                cart.setName(dish.getName());
                cart.setAmount(dish.getPrice());
                cart.setImage(dish.getImage());
            }
            else
            {
                Setmeal setmeal = setmealMapper.getById(shoppingCartDTO.getSetmealId());
                cart.setName(setmeal.getName());
                cart.setAmount(setmeal.getPrice());
                cart.setImage(setmeal.getImage());

            }
            cart.setNumber(1);
            cart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(cart);
        }
    }


    public List<ShoppingCart> listShoppingCart() {
        Long userId = BaseContext.getCurrentId();
        ShoppingCart cart =ShoppingCart.builder().userId(userId).build();
        List<ShoppingCart> list = shoppingCartMapper.list(cart);
        return list;
    }


    public void cleanShoppingCart() {
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.delete(userId);
    }

}
