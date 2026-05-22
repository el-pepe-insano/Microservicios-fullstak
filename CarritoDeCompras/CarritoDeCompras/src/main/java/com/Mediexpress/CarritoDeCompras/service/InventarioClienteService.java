package com.Mediexpress.CarritoDeCompras.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.Mediexpress.CarritoDeCompras.model.Producto;

import reactor.core.publisher.Mono;

@Service
public class InventarioClienteService {

    private final WebClient webClient;

    public InventarioClienteService(
            @Qualifier("webClientInventario") WebClient webClient) {
        this.webClient = webClient;
    }

    public Producto obtenerProducto(Long idProducto) {
        return webClient.get()
                .uri("/productos/" + idProducto)
                .retrieve()
                .bodyToMono(Producto.class)
                .onErrorResume(e -> Mono.empty())
                .block();
    }
}