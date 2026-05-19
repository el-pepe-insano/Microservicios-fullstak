package com.Mediexpress.CarritoDeCompras.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
@Configuration
public class config_carrito {
    @Bean
    public OpenAPI carritoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Carrito de Compras - MediExpress")
                        .description("Servicio para la gestión del carrito de compras, incluyendo productos agregados por cliente.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Equipo MediExpress")
                                .email("soporte@mediexpress.cl")
                                .url("https://www.mediexpress.cl"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}


