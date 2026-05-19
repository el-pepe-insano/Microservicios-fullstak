package com.example.ConsultarInventario.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productos")
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del producto", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre del producto", example = "Paracetamol")
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Descripción detallada del producto", example = "Paracetamol 500 mg en tabletas")
    private String descripcion;

    @Column(nullable = false)
    @Schema(description = "Cantidad disponible en inventario", example = "150")
    private int cantidad;

    @Column(nullable = false)
    @Schema(description = "Precio unitario del producto", example = "750.50")
    private double precio;
}


