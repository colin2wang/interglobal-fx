package com.globalfx.crm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.globalfx.common", "com.globalfx.crm"})
@MapperScan("com.globalfx.crm.mapper")
public class FxCrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(FxCrmApplication.class, args);
    }
}
