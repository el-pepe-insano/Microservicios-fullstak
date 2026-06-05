package com.example.ResenasYcalificaciones.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;
@Data
@Schema(description = "DTO de respuesta con datos de la reseña")
public class ReseniaResponseDTO {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "1")
    private Long idProducto;
    @Schema(example = "2")
    private Long idCliente;
    @Schema(example = "5")
    private int calificacion;
    @Schema(example = "Excelente medicamento.")
    private String comentario;
    @Schema(example = "2025-07-10T15:30:00")
    private LocalDateTime fechaCreacion;
}
