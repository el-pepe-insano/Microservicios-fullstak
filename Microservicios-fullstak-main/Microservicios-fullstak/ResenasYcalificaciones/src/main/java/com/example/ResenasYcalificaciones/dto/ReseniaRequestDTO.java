package com.example.ResenasYcalificaciones.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
@Schema(description = "DTO para crear una reseña")
public class ReseniaRequestDTO {
    @NotNull(message = "El ID del producto es obligatorio.")
    @Schema(example = "1")
    private Long idProducto;

    @NotNull(message = "El ID del cliente es obligatorio.")
    @Schema(example = "2")
    private Long idCliente;

    @Min(value = 1, message = "La calificación mínima es 1.")
    @Max(value = 5, message = "La calificación máxima es 5.")
    @Schema(example = "5")
    private int calificacion;

    @NotBlank(message = "El comentario no puede estar vacío.")
    @Size(max = 500, message = "El comentario no puede superar 500 caracteres.")
    @Schema(example = "Excelente medicamento, alivió mi dolor rápidamente.")
    private String comentario;
}
