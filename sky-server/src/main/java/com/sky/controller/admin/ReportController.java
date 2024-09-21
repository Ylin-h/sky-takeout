package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/admin/report")
@Slf4j
@Api(tags = "报表管理")
public class ReportController {
    @Autowired
    private ReportService reportService;
    @GetMapping("/turnoverStatistics")
    @ApiOperation(value = "营业额统计", notes = "营业额统计")
    public Result<TurnoverReportVO> turnoverReport(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin ,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("turnoverReport begin:{}, end:{}", begin, end);
        return Result.success(reportService.getTurnoverStatistics(begin, end));
            }

   @GetMapping("/userStatistics")
   @ApiOperation(value = "用户统计", notes = "用户统计")
   public Result<UserReportVO> getUserReport(
           @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin ,
           @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
       log.info("getUserReport begin:{}, end:{}" , begin, end);
       return Result.success(reportService.getUserReport(begin, end));
           }
   @GetMapping("/ordersStatistics")
    @ApiOperation(value = "订单统计", notes = "订单统计")
    public Result<OrderReportVO> getOrderReport(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin ,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("getOrderReport begin:{}, end:{}", begin, end);
        return Result.success(reportService.getOrderReport(begin, end));
   }
   /**
     * 销量排行榜
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/top10")
    @ApiOperation(value = "销量排行榜", notes = "销量排行榜")
    public Result<SalesTop10ReportVO> gettop10Report(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin ,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("销量排行榜 begin:{}, end:{}", begin, end);
        return Result.success(reportService.gettop10Report(begin, end));
    }
}
