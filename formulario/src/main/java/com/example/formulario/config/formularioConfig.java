package com.example.formulario.config;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class formularioConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API Formularios")
                .version("1.0")
                .description("API para manejar formularios de consultas y problemas")
                .contact(new Contact()
                    .name("Diego")
                    .email("diego@ejemplo.com")));
    }

    @Bean
    public GroupedOpenApi formularioApi() {
        return GroupedOpenApi.builder()
            .group("formularios")
            .packagesToScan("com.mediexpress.formulario.controller")
            .build();
    }
}


