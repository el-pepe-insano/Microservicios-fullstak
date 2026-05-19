package com.example.ResenasYcalificaciones.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de Reseñas y Calificaciones")
                .version("v1.0.0")
                .description("API REST para gestionar reseñas y calificaciones de productos")
                .contact(new Contact()
                    .name("Tu Nombre o Equipo")
                    .email("tu-email@ejemplo.com")
                    .url("https://tusitio.com"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("http://springdoc.org")))
            .externalDocs(new ExternalDocumentation()
                .description("Documentación del proyecto")
                .url("https://github.com/tuusuario/tu-repo"));
    }
}

