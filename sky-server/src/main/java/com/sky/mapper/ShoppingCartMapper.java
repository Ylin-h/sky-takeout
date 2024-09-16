package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

   List<ShoppingCart> list(ShoppingCart cart);

   @Update("update shopping_cart set number = #{number} where id = #{id}")
    void update(ShoppingCart cart);

    void insert(ShoppingCart cart);
    @Delete("delete from shopping_cart where id = #{userid}")
    void delete(Long userId);
    @Delete("delete from shopping_cart where user_id = #{userid}")
    void deleteByUserId(Long userId);
}
