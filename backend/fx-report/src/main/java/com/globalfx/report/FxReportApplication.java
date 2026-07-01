package com.globalfx.report;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.globalfx.common", "com.globalfx.report"})
@MapperScan("com.globalfx.report.mapper")
public class FxReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(FxReportApplication.class, args);
    }
}
