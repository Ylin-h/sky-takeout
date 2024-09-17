package com.sky.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component//交给springIOC管理
public class MyTask {
//    @Scheduled(cron = "0/5 * * * * ?")//每5秒执行一次
    public void execute() {
        log.info("MyTask execute:{}",new Date());
    }
}
