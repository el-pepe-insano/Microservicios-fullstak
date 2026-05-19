package com.Mediexpress.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de Usuarios - MediExpress")
                .version("1.0")
                .description("Microservicio de gestión de usuarios de MediExpress")
                .contact(new Contact()
                    .name("Equipo MediExpress")
                    .email("contacto@mediexpress.cl")
                )
            );
    }
}