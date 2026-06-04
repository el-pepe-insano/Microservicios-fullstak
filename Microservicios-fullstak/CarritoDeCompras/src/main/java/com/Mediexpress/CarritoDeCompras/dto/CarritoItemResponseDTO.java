package com.Mediexpress.CarritoDeCompras.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
@Schema(description = "DTO de respuesta con datos del ítem del carrito")
public class CarritoItemResponseDTO {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "1")
    private Long idCliente;
    @Schema(example = "2")
    private Long idProducto;
    @Schema(example = "Paracetamol")
    private String nombreProducto;
    @Schema(example = "3")
    private Integer cantidad;
    @Schema(example = "1200.50")
    private Double precioUnitario;
    @Schema(example = "3601.50")
    private Double subtotal;
}
