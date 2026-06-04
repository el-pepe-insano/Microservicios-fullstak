package com.example.ConsultarInventario.config;

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
    public OpenAPI productosOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de Productos - MediExpress")
                .description("Documentación de la API REST para gestión de productos del sistema MediExpress.")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Equipo MediExpress")
                    .email("soporte@mediexpress.cl")
                    .url("https://mediexpress.cl"))
                .license(new License()
                    .name("Licencia MIT")
                    .url("https://opensource.org/licenses/MIT"))
            )
            .externalDocs(new ExternalDocumentation()
                .description("Repositorio del proyecto")
                .url("https://github.com/mediexpress/productos-service"));
    }
}

