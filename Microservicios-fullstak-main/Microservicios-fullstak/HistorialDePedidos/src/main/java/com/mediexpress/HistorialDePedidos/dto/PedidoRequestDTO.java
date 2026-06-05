package com.mediexpress.HistorialDePedidos.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
@Data
@Schema(description = "DTO para crear un nuevo pedido")
public class PedidoRequestDTO {
    @NotNull(message = "El ID del cliente es obligatorio.")
    @Schema(example = "1")
    private Long idCliente;

    @NotNull(message = "La fecha es obligatoria.")
    @Schema(example = "2025-07-10")
    private LocalDate fecha;

    @NotNull @DecimalMin(value = "0.01", message = "El total debe ser mayor a 0.")
    @Schema(example = "15000.50")
    private Double total;

    @NotBlank(message = "El estado es obligatorio.")
    @Schema(example = "PENDIENTE", allowableValues = {"PENDIENTE","EN_PROCESO","ENVIADO","ENTREGADO","CANCELADO"})
    private String estado;
}
