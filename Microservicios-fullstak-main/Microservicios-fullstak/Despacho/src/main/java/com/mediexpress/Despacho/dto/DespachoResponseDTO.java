package com.mediexpress.Despacho.dto;
import com.mediexpress.Despacho.model.EstadoDespacho;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;
@Data
@Schema(description = "DTO de respuesta con datos del despacho")
public class DespachoResponseDTO {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "1")
    private Long idPedido;
    @Schema(example = "2")
    private Long idCliente;
    @Schema(example = "Av. Las Flores 123, Santiago")
    private String direccionEntrega;
    @Schema(example = "EN_CAMINO")
    private EstadoDespacho estado;
    @Schema(example = "MED-00042")
    private String codigoSeguimiento;
    @Schema(example = "2025-07-10T10:00:00")
    private LocalDateTime fechaCreacion;
    @Schema(example = "2025-07-13T18:00:00")
    private LocalDateTime fechaEntrega;
}
