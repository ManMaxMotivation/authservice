package com.bankapp.auth.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bank Application API")
                        .version("1.0")
                        .description("API for user authentication and management in Bank Application")
                        .contact(new Contact()
                                .name("Alex")
                                .email("my@ya.ru")
                                .url("https://example.com"))
                );
    }
}
