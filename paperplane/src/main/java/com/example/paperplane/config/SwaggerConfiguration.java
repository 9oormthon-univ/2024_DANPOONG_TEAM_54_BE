package com.example.paperplane.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .components(new Components());
    }

    private Info apiInfo() {
        return new Info().title("PaperPlane Swagger API 명세서")
                .description("Springdoc을 사용한 PaperPlane Swagger docs")
                .version("1.0.0");
    }
}
