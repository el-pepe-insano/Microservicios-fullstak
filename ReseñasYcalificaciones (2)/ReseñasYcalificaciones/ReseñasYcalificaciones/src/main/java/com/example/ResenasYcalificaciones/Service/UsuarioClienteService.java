package com.example.ResenasYcalificaciones.Service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.ResenasYcalificaciones.model.Usuario;

import reactor.core.publisher.Mono;

@Service
public class UsuarioClienteService {

    private final WebClient webClient;

    public UsuarioClienteService(
            @Qualifier("webClientUsuarios") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Usuario> obtenerUsuarioPorId(Long id) {
        return webClient.get()
                .uri("/api/v1/usuarios/" + id)
                .retrieve()
                .bodyToMono(Usuario.class)
                .onErrorResume(e -> Mono.empty());
    }
}