package com.mediexpress.GestionPagos.dto;
import com.mediexpress.GestionPagos.model.MetodoPago;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
@Schema(description = "DTO para procesar un pago")
public class PagoRequestDTO {
    @NotNull(message = "El ID del pedido es obligatorio.")
    @Schema(example = "1")
    private Long idPedido;

    @NotNull(message = "El ID del cliente es obligatorio.")
    @Schema(example = "2")
    private Long idCliente;

    @NotNull @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0.")
    @Schema(example = "15000.50")
    private Double monto;

    @NotNull(message = "El método de pago es obligatorio.")
    @Schema(example = "TARJETA", allowableValues = {"TARJETA","TRANSFERENCIA","EFECTIVO"})
    private MetodoPago metodoPago;
}
