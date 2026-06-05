package com.mediexpress.Despacho.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
@Schema(description = "DTO para crear un despacho")
public class DespachoRequestDTO {
    @NotNull(message = "El ID del pedido es obligatorio.")
    @Schema(example = "1")
    private Long idPedido;

    @NotNull(message = "El ID del cliente es obligatorio.")
    @Schema(example = "2")
    private Long idCliente;

    @NotBlank(message = "La dirección de entrega es obligatoria.")
    @Schema(example = "Av. Las Flores 123, Santiago")
    private String direccionEntrega;
}
