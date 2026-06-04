package com.Mediexpress.CarritoDeCompras.service;

import com.Mediexpress.CarritoDeCompras.model.Producto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class InventarioClienteService {

    private final WebClient webClient;

    public InventarioClienteService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
    }

    public Producto obtenerProducto(Long idProducto) {
        try {
            // 1. Extraer el Token JWT de la petición actual que llegó al Carrito
            String tokenHeader = null;
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                tokenHeader = request.getHeader("Authorization"); // Aquí viene el "Bearer eyJ..."
            }

            // 2. Preparar la llamada GET al Inventario
            WebClient.RequestHeadersSpec<?> requestSpec = this.webClient.get()
                    .uri("/productos/{id}", idProducto);

            // 3. Si el Token existe, se lo "inyectamos" a la llamada del WebClient
            if (tokenHeader != null) {
                requestSpec = requestSpec.header("Authorization", tokenHeader);
            }

            // 4. Ejecutar la llamada
            return requestSpec.retrieve()
                    .bodyToMono(Producto.class)
                    .block();

        } catch (Exception e) {
            System.out.println("🚨 ERROR AL CONECTAR CON INVENTARIO: " + e.getMessage());
            e.printStackTrace(); // Esto te mostrará el error real en la consola de VS Code si algo más falla
            return null;
        }
    }
}