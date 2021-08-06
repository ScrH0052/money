package com.scrh.money.dataservice;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 数据服务层启动类
 *
 * @author ScrH0052
 * @date 2021/7/31
 */
@EnableDubbo
@SpringBootApplication
@MapperScan("com.scrh.money.dataservice.mapper")
public class DataServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataServiceApplication.class, args);
    }
}
