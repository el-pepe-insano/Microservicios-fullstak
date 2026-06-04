package com.mediexpress.Despacho.model;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "despachos")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Entidad que representa el despacho de un pedido en MediExpress")
public class Despacho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del despacho", example = "1")
    private Long id;

    @Column(nullable = false, unique = true)
    @Schema(description = "ID del pedido asociado al despacho", example = "10")
    private Long idPedido;

    @Column(nullable = false)
    @Schema(description = "ID del cliente destinatario", example = "5")
    private Long idCliente;

    @Column(nullable = false)
    @Schema(description = "Dirección de entrega", example = "Av. Las Flores 123, Santiago")
    private String direccionEntrega;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Estado del despacho: PREPARANDO, EN_CAMINO, ENTREGADO, DEVUELTO", example = "PREPARANDO")
    private EstadoDespacho estado;

    @Column(name = "fecha_creacion")
    @Schema(description = "Fecha en que se creó el despacho")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_entrega")
    @Schema(description = "Fecha estimada de entrega")
    private LocalDateTime fechaEntrega;

    @Column(name = "codigo_seguimiento", unique = true)
    @Schema(description = "Código de seguimiento del despacho", example = "MED-00001")
    private String codigoSeguimiento;

    @PrePersist
    public void asignarDefaults() {
        this.fechaCreacion = LocalDateTime.now();
        if (this.estado == null) this.estado = EstadoDespacho.PREPARANDO;
        if (this.codigoSeguimiento == null)
            this.codigoSeguimiento = "MED-" + String.format("%05d", (long)(Math.random() * 99999));
    }
}
