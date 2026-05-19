package com.mediexpress.HistorialDePedidos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${servicio.usuarios.url}")
    private String usuariosBaseUrl;

    @Bean
    public WebClient webClientUsuarios() {
        return WebClient.builder()
                .baseUrl(usuariosBaseUrl)
                .build();
    }
}


