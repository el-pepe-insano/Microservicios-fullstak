package com.mediexpress.GestionPagos.model;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "pagos")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Entidad que representa un pago de un pedido en MediExpress")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del pago", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "ID del pedido asociado", example = "10")
    private Long idPedido;

    @Column(nullable = false)
    @Schema(description = "ID del cliente que realiza el pago", example = "5")
    private Long idCliente;

    @Column(nullable = false)
    @Schema(description = "Monto total del pago", example = "15000.50")
    private Double monto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Método de pago: TARJETA, TRANSFERENCIA, EFECTIVO", example = "TARJETA")
    private MetodoPago metodoPago;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Estado del pago: PENDIENTE, APROBADO, RECHAZADO, ANULADO", example = "PENDIENTE")
    private EstadoPago estado;

    @Column(name = "fecha_pago")
    @Schema(description = "Fecha y hora en que se realizó el pago")
    private LocalDateTime fechaPago;

    @PrePersist
    public void asignarFecha() {
        this.fechaPago = LocalDateTime.now();
        if (this.estado == null) this.estado = EstadoPago.PENDIENTE;
    }
}
