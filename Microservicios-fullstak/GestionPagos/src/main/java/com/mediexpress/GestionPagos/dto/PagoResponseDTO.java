package com.mediexpress.GestionPagos.dto;
import com.mediexpress.GestionPagos.model.EstadoPago;
import com.mediexpress.GestionPagos.model.MetodoPago;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;
@Data
@Schema(description = "DTO de respuesta con datos del pago")
public class PagoResponseDTO {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "1")
    private Long idPedido;
    @Schema(example = "2")
    private Long idCliente;
    @Schema(example = "15000.50")
    private Double monto;
    @Schema(example = "TARJETA")
    private MetodoPago metodoPago;
    @Schema(example = "APROBADO")
    private EstadoPago estado;
    @Schema(example = "2025-07-10T15:30:00")
    private LocalDateTime fechaPago;
}
