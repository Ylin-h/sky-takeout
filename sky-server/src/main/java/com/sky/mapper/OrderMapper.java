package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    Double sumByMap(Map map);

    /**
     * 插入订单数据
     * @param order
     */
    void insert(Orders order);

    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);
    @Select("select * from orders where status = #{status} and order_time <= #{orderTime}")
    List<Orders> getOrderStatus(Integer status, LocalDateTime orderTime);


    Integer countByMap(Map map);

    List<GoodsSalesDTO> getTop10ByMap(LocalDateTime begin, LocalDateTime end);
}
