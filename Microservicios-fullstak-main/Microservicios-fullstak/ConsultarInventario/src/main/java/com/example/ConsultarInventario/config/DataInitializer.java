package com.example.ConsultarInventario.config;

import com.example.ConsultarInventario.model.Producto;
import com.example.ConsultarInventario.repository.ProductoRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(ProductoRepositorio repository) {
        return args -> {
            // Verificamos si la base de datos está vacía
            if (repository.count() == 0) {
                System.out.println("📦 La base de datos está vacía. Precargando productos por defecto...");

                // Creamos los productos usando el @Builder de tu modelo
                Producto p1 = Producto.builder()
                        .nombre("Paracetamol")
                        .descripcion("Caja de 20 tabletas de 500mg")
                        .cantidad(150)
                        .precio(1200.50)
                        .build();

                Producto p2 = Producto.builder()
                        .nombre("Ibuprofeno")
                        .descripcion("Caja de 10 cápsulas de 400mg blando")
                        .cantidad(85)
                        .precio(2500.00)
                        .build();

                Producto p3 = Producto.builder()
                        .nombre("Amoxicilina")
                        .descripcion("Frasco de suspensión oral 250mg/5ml")
                        .cantidad(40)
                        .precio(4300.00)
                        .build();

                Producto p4 = Producto.builder()
                        .nombre("Alcohol Gel")
                        .descripcion("Botella de 500ml con dosificador")
                        .cantidad(200)
                        .precio(3100.00)
                        .build();

                Producto p5 = Producto.builder()
                        .nombre("Mascarillas KN95")
                        .descripcion("Caja de 50 unidades color blanco")
                        .cantidad(300)
                        .precio(5000.00)
                        .build();

                // Guardamos todos los productos de golpe
                repository.saveAll(List.of(p1, p2, p3, p4, p5));
                
                System.out.println("✅ ¡5 Productos precargados con éxito en la base de datos!");
            } else {
                System.out.println("⚡ Ya existen productos en la base de datos. Saltando precarga...");
            }
        };
    }
}