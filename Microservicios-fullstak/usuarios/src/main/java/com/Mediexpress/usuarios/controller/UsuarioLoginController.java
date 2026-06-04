package com.Mediexpress.usuarios.controller;

import com.Mediexpress.usuarios.model.UsuarioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/login")
public class UsuarioLoginController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @PostMapping("/iniciar-sesion")
    public Mono<ResponseEntity<Object>> iniciarSesion(@RequestParam String correo,
                                                      @RequestParam String contraseña) {
        return webClientBuilder.build()
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/iniciar")
                        .queryParam("correo", correo)
                        .queryParam("contraseña", contraseña)
                        .build())
                .retrieve()
                .bodyToMono(UsuarioLogin.class)
                .map(usuario -> ResponseEntity.ok().body((Object) usuario))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body((Object) "Credenciales incorrectas")));
    }
}


