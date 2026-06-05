package com.mediexpress.HistorialDePedidos.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDate;
@Data
@Schema(description = "DTO de respuesta con datos del pedido")
public class PedidoResponseDTO {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "1")
    private Long idCliente;
    @Schema(example = "Juan Pérez")
    private String nombreCliente;
    @Schema(example = "2025-07-10")
    private LocalDate fecha;
    @Schema(example = "15000.50")
    private Double total;
    @Schema(example = "PENDIENTE")
    private String estado;
}
