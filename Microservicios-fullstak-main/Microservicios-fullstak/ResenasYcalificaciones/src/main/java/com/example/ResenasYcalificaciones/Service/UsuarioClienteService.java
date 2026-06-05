package com.example.ResenasYcalificaciones.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;
import com.example.ResenasYcalificaciones.model.Usuario;
import reactor.core.publisher.Mono;

@Service
public class UsuarioClienteService {

    private final WebClient webClient;

    public UsuarioClienteService() {
        // Conexión fija a la IP del microservicio de Usuarios (Puerto 8082)
        this.webClient = WebClient.builder()
                .baseUrl("http://127.0.0.1:8082")
                .build();
    }

    public Mono<Usuario> obtenerUsuarioPorId(Long id) {
        // 🔑 Truco: Capturamos el Token JWT que enviaste desde Postman a este microservicio
        String token = null;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            token = request.getHeader("Authorization");
        }

        // Si por alguna razón el token no viene con "Bearer ", se lo agregamos
        String authHeader = (token != null && token.startsWith("Bearer ")) ? token : "Bearer " + token;

        return webClient.get()
                .uri("/api/usuarios/" + id)
                .header("Authorization", authHeader) // 👈 Le pasamos el Token al microservicio de Usuarios
                .retrieve()
                .bodyToMono(Usuario.class)
                .onErrorResume(e -> {
                    System.out.println("\n🛑 Fallo WebClient en Reseñas (Conectando a Usuarios): " + e.getMessage() + "\n");
                    return Mono.empty();
                });
    }
}