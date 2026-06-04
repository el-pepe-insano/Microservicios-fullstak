package com.mediexpress.Despacho.config;
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
    public WebClient webClientPagos(@Value("${servicio.pagos.url}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }
}
