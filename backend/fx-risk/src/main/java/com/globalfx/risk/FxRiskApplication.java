package com.globalfx.risk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.globalfx.common", "com.globalfx.risk"})
@MapperScan("com.globalfx.risk.mapper")
public class FxRiskApplication {

    public static void main(String[] args) {
        SpringApplication.run(FxRiskApplication.class, args);
    }
}
