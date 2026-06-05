package com.Mediexpress.CarritoDeCompras.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;
@Data
@Schema(description = "DTO con el resumen completo del carrito de un cliente")
public class CarritoResumenDTO {
    @Schema(example = "1")
    private Long idCliente;
    private List<CarritoItemResponseDTO> items;
    @Schema(example = "5")
    private int totalProductos;
    @Schema(example = "15000.50")
    private Double totalPrecio;
}
