package com.Mediexpress.CarritoDeCompras.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
@Schema(description = "DTO para agregar o actualizar un ítem en el carrito")
public class CarritoItemRequestDTO {
    @NotNull(message = "El ID del cliente es obligatorio.")
    @Schema(example = "1")
    private Long idCliente;

    @NotNull(message = "El ID del producto es obligatorio.")
    @Schema(example = "2")
    private Long idProducto;

    @Min(value = 1, message = "La cantidad debe ser al menos 1.")
    @Max(value = 100, message = "La cantidad no puede superar 100.")
    @Schema(example = "3")
    private Integer cantidad;
}
