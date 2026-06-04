package com.example.ConsultarInventario.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
@Schema(description = "DTO para crear o actualizar un producto")
public class ProductoRequestDTO {
    @NotBlank(message = "El nombre es obligatorio.")
    @Schema(example = "Paracetamol")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria.")
    @Schema(example = "Caja de 20 tabletas de 500mg")
    private String descripcion;

    @Min(value = 0, message = "La cantidad no puede ser negativa.")
    @Schema(example = "100")
    private int cantidad;

    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0.")
    @Schema(example = "1200.50")
    private double precio;
}
