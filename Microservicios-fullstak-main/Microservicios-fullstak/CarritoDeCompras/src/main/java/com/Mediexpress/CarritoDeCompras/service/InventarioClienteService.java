package com.Mediexpress.CarritoDeCompras.service;

import com.Mediexpress.CarritoDeCompras.model.Producto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class InventarioClienteService {

    private final WebClient webClient;

    public InventarioClienteService(@Qualifier("webClientInventario") WebClient webClient) {
        // Forzamos al WebClient a usar la IP IPv4 fija para evitar el rechazo de conexión (Connection refused)
        this.webClient = webClient.mutate()
                .baseUrl("http://127.0.0.1:8081")
                .build();
    }

    public Producto obtenerProducto(Long idProducto, String token) {
        try {
            return webClient.get()
                    .uri("/productos/" + idProducto)
                    // Aseguramos que el token lleve la palabra Bearer obligatoria si no la tiene
                    .header("Authorization", token != null && token.startsWith("Bearer ") ? token : "Bearer " + token)
                    .retrieve()
                    .bodyToMono(Producto.class)
                    .onErrorResume(e -> {
                        // Esto imprimirá en la consola del Carrito el error exacto si algo falla
                        System.out.println("\n🛑 Fallo WebClient en el Carrito: " + e.getMessage() + "\n");
                        return Mono.empty();
                    })
                    .block();
        } catch (Exception e) {
            System.out.println("\n🚨 Error crítico al conectar con Inventario: " + e.getMessage() + "\n");
            return null;
        }
    }
}