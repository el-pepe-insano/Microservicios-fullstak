package com.mediexpress.HistorialDePedidos.model;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un pedido realizado por un cliente")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del pedido", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(nullable = false)
    @Schema(description = "ID del cliente que realizó el pedido", example = "123")
    private Long idCliente;

    @Column(nullable = false)
    @Schema(description = "Fecha en que se realizó el pedido", example = "2025-07-07")
    private LocalDate fecha;

    @Column(nullable = false)
    @Schema(description = "Total monetario del pedido", example = "15000.50")
    private Double total;

    @Column(nullable = false)
    @Schema(description = "Estado actual del pedido", example = "PENDIENTE")
    private String estado;

}