package com.globalfx.clearing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.globalfx.common", "com.globalfx.clearing"})
@MapperScan("com.globalfx.clearing.mapper")
public class FxClearingApplication {

    public static void main(String[] args) {
        SpringApplication.run(FxClearingApplication.class, args);
    }
}
