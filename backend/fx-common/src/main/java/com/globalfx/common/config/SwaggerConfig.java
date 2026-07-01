package com.globalfx.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("环球汇通外汇交易平台 API")
                        .description("GlobalFX Trading Platform REST API Documentation")
                        .version("1.0.0"));
    }
}
