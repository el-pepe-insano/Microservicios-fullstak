package com.mediexpress.GestionPagos.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClientPedidos(@Value("${servicio.pedidos.url}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }
    @Bean
    public WebClient webClientUsuarios(@Value("${servicio.usuarios.url}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }
}
