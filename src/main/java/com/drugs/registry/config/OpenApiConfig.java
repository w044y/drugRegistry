// ================================
// 1. OpenAPI Configuration
// ================================
package com.drugs.registry.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Polish Pharmaceutical Products Registry API")
                        .description("API for managing Polish pharmaceutical products registry data")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Pharmaceutical Registry Team")
                                .email("support@pharma-registry.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}