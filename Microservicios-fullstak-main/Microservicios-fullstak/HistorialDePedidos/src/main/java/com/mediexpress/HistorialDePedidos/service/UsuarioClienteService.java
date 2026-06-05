package com.mediexpress.HistorialDePedidos.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;
import com.mediexpress.HistorialDePedidos.model.Usuario;
import reactor.core.publisher.Mono;

@Service
public class UsuarioClienteService {

    private final WebClient webClient;

    public UsuarioClienteService() {
        this.webClient = WebClient.builder()
                .baseUrl("http://127.0.0.1:8082")
                .build();
    }

    public Mono<Usuario> obtenerUsuarioPorId(Long id) {
        String token = null;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            token = request.getHeader("Authorization");
        }

        String authHeader = (token != null && token.startsWith("Bearer ")) ? token : "Bearer " + token;

        System.out.println("\n📬 Historial enviando petición a Usuarios para ID: " + id);

        return webClient.get()
                .uri("/api/usuarios/" + id)
                .header("Authorization", authHeader)
                .retrieve()
                .bodyToMono(Usuario.class)
                .doOnNext(usuario -> System.out.println("✅ Usuario recibido con éxito: " + usuario.getNombreUsuario()))
                .onErrorResume(e -> {
                    // Si hay un error de autenticación (401) o de mapeo, lo sabremos aquí de inmediato
                    System.out.println("🛑 Fallo WebClient en Historial al consultar usuario: " + e.getMessage() + "\n");
                    return Mono.empty();
                });
    }
}