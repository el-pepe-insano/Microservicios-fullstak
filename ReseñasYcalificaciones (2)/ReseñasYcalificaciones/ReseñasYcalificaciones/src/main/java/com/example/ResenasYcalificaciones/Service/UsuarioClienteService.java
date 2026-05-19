package com.example.ResenasYcalificaciones.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.ResenasYcalificaciones.model.Usuario;
import reactor.core.publisher.Mono;

@Service
public class UsuarioClienteService {

    private final WebClient webClient;

    public UsuarioClienteService(@Value("${servicio.usuarios.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<Usuario> obtenerUsuarioPorId(Long id) {
        return webClient.get()
                .uri("/api/v1/usuarios/" + id)
                .retrieve()
                .bodyToMono(Usuario.class);
    }
}



