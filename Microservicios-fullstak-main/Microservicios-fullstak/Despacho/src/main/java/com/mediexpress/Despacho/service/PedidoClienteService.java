package com.mediexpress.Despacho.service;
import com.mediexpress.Despacho.model.Pedido;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Service
public class PedidoClienteService {
    private final WebClient webClient;
    public PedidoClienteService(@Qualifier("webClientPedidos") WebClient webClient) {
        this.webClient = webClient;
    }
    public Pedido obtenerPedido(Long idPedido) {
        return webClient.get()
                .uri("/api/v1/pedidos/" + idPedido)
                .retrieve()
                .bodyToMono(Pedido.class)
                .onErrorResume(e -> Mono.empty())
                .block();
    }
}
