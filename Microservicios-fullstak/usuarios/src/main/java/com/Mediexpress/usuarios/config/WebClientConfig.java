package com.Mediexpress.usuarios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8083") // IP o puerto del microservicio login
                .build();
    }
    @Bean
public WebClient.Builder webClientBuilder() {
    return WebClient.builder();
}
}

