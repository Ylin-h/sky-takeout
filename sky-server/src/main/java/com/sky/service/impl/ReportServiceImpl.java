package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 统计指定时间区间内的营业额数据
     *
     * @param begin
     * @param end
     * @return
     */
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        //当前集合用于存放从begin到end范围内的每天的日期
        List<LocalDate> dateList = new ArrayList<>();

        dateList.add(begin);

        while (!begin.equals(end)) {
            //日期计算，计算指定日期的后一天对应的日期
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        //存放每天的营业额
        List<Double> turnoverList = new ArrayList<>();
        for (LocalDate date : dateList) {
            //查询date日期对应的营业额数据，营业额是指：状态为“已完成”的订单金额合计
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            // select sum(amount) from orders where order_time > beginTime and order_time < endTime and status = 5
            Map map = new HashMap();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", Orders.COMPLETED);
            Double turnover = orderMapper.sumByMap(map);
            turnover = turnover == null ? 0.0 : turnover;
            turnoverList.add(turnover);
        }

        //封装返回结果
        return TurnoverReportVO
                .builder()
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();
    }

    /**
     * 统计指定时间区间内的用户数据
     *
     * @param begin
     * @param end
     * @return
     */
    public UserReportVO getUserReport(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();

        dateList.add(begin);

        while (!begin.equals(end)) {
            //日期计算，计算指定日期的后一天对应的日期
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        List<Integer> newUserList = new ArrayList<>();//新增用户
        List<Integer> totalUserList = new ArrayList<>();//总用户

        for (LocalDate date : dateList) {
            //查询date日期对应的新增用户数据
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            // select count(distinct user_id) from orders where order_time > beginTime and order_time < endTime
            Map map = new HashMap();

            map.put("end", endTime);

            Integer totalUser = userMapper.countDistinctByMap(map);
            totalUser = totalUser == null ? 0 : totalUser;
            totalUserList.add(totalUser);
            map.put("begin", beginTime);
            Integer newUser = userMapper.countDistinctByMap(map);
            newUser = newUser == null ? 0 : newUser;
            newUserList.add(newUser);

        }
        //封装返回结果
        return UserReportVO
                .builder()
                .dateList(StringUtils.join(dateList, ","))
                .newUserList(StringUtils.join(newUserList, ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .build();
    }

    /**
     * 统计指定时间区间内的订单数据
     *
     * @param begin
     * @param end
     * @return
     */
    public OrderReportVO getOrderReport( LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();

        dateList.add(begin);

        while (!begin.equals(end)) {
            //日期计算，计算指定日期的后一天对应的日期
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        List<Integer> totalOrderList = new ArrayList<>();//总订单
        List<Integer> validOrderList = new ArrayList<>();//有效订单

        for (LocalDate date : dateList) {
            //查询date日期对应的订单数据
             LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
//            dateCount(beginTime, endTime, null);
            totalOrderList.add(dateCount(beginTime, endTime, null));
            validOrderList.add(dateCount(beginTime, endTime, Orders.COMPLETED));
            }
        Integer totalOrder = totalOrderList.stream().reduce(Integer::sum).get();
        Integer validOrder = validOrderList.stream().reduce(Integer::sum).get();
        Double completeRate = totalOrder==0?0.0:(double) validOrder / totalOrder * 100;
        //封装返回结果
        return OrderReportVO
                .builder()
                .dateList(StringUtils.join(dateList, ","))
                .orderCountList(StringUtils.join(totalOrderList, ","))
                .validOrderCountList(StringUtils.join(validOrderList, ","))
                .totalOrderCount(totalOrder)
                .validOrderCount(validOrder)
                .orderCompletionRate(completeRate)
                .build();
    }

    /**
     * 统计指定时间区间内的销售排行榜数据
     *
     * @param begin
     * @param end
     * @return
     */
    public SalesTop10ReportVO gettop10Report(LocalDate begin, LocalDate end) {
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
       List<GoodsSalesDTO> goodsSalesDTOList = orderMapper.getTop10ByMap(beginTime, endTime);
        List<String> names = goodsSalesDTOList.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList());
        List<Integer> numbers = goodsSalesDTOList.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList());


        return SalesTop10ReportVO.builder()
                .nameList(StringUtils.join(names, ","))
                .numberList(StringUtils.join(numbers, ","))
                .build();
    }

    private Integer dateCount (LocalDateTime beginTime, LocalDateTime endTime, Integer status)
        {

            Map map = new HashMap();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", status);
            return orderMapper.countByMap(map);
        }
}
