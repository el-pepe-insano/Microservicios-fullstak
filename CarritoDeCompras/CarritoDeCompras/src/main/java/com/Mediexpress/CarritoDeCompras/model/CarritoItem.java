package com.Mediexpress.CarritoDeCompras.model;





import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Carrito_Item")
@Schema(description = "Entidad que representa un ítem dentro del carrito de compras")
public class CarritoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del ítem en el carrito", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "ID del cliente al que pertenece el carrito", example = "101")
    private Long idCliente;

    @Column(nullable = false)
    @Schema(description = "ID del producto agregado al carrito", example = "202")
    private Long idProducto;

    @Column(nullable = false)
    @Schema(description = "Cantidad del producto en el carrito", example = "3")
    private Integer cantidad;
}
