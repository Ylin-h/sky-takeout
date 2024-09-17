package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class ProcessOrderTask {
    @Autowired
    private OrderMapper orderMapper;
//    @Scheduled(cron = "0 0/1 * * *?")

//订单15分钟未支付，每分钟执行一次去处理订单支付状态
    @Scheduled(cron = "0/5 * * * * ?")//每5秒执行一次
    public void processOrder() {
        log.info("处理订单超时未支付");
        LocalDateTime orderTime = LocalDateTime.now().minusMinutes(15);
        List<Orders> list =  orderMapper.getOrderStatus(Orders.PENDING_PAYMENT, orderTime);
        if (list!= null && !list.isEmpty()) {
            for (Orders order : list) {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("订单超时未支付");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            }
        }
    }
    //处理派送状态
//    @Scheduled(cron = "0 0/1 * * * ?")
    @Scheduled(cron = "1/5 * * * * ?")//每5秒执行一次
    public void processDelivery() {
        log.info("Processing delivery");
        LocalDateTime orderTime = LocalDateTime.now().minusMinutes(60);
        List<Orders> list =  orderMapper.getOrderStatus(Orders.DELIVERY_IN_PROGRESS, orderTime);
        if (list!= null && !list.isEmpty()) {
            for (Orders order : list) {
                order.setStatus(Orders.COMPLETED);
                orderMapper.update(order);
            }
        }
    }
}
