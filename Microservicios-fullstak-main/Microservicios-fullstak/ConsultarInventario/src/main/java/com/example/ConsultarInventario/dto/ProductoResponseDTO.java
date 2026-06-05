package com.example.ConsultarInventario.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
@Schema(description = "DTO de respuesta con datos del producto")
public class ProductoResponseDTO {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "Paracetamol")
    private String nombre;
    @Schema(example = "Caja de 20 tabletas de 500mg")
    private String descripcion;
    @Schema(example = "100")
    private int cantidad;
    @Schema(example = "1200.50")
    private double precio;
    @Schema(description = "Indica si hay stock disponible")
    private boolean disponible;
}
