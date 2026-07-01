package com.globalfx.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.globalfx.common", "com.globalfx.system"})
@MapperScan("com.globalfx.system.mapper")
public class FxSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FxSystemApplication.class, args);
    }
}
