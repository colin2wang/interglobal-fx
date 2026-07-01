package com.globalfx.trade;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.globalfx.common", "com.globalfx.trade"})
@MapperScan("com.globalfx.trade.mapper")
public class FxTradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FxTradeApplication.class, args);
    }
}
