package com.Mediexpress.usuarios.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.Mediexpress.usuarios.model.UsuarioLogin;

import reactor.core.publisher.Mono;

@Service
public class LoginClienteService {
    private final WebClient webClient;

    public LoginClienteService(WebClient.Builder webClientBuilder,
                                @Value("${servicio.login.url}") String baseUrl) {
        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<UsuarioLogin> obtenerUsuarioPorCorreo(String correo) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/login/buscar")
                        .queryParam("correo", correo)
                        .build())
                .retrieve()
                .bodyToMono(UsuarioLogin.class);
    }
}



