package com.example.ResenasYcalificaciones.model;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "resenias")
@Schema(description = "Modelo que representa una reseña de un producto realizada por un cliente")
public class Resenia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la reseña", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name = "producto_id", nullable = false)
    @Schema(description = "ID del producto al que pertenece la reseña", example = "123", required = true)
    private Long idProducto;

    @Column(name = "cliente_id", nullable = false)
    @Schema(description = "ID del cliente que realiza la reseña", example = "456", required = true)
    private Long idCliente;
    
    @Column(nullable = false)
    @Schema(description = "Calificación otorgada al producto (de 1 a 5)", example = "4", required = true)
    private int calificacion;

    @Column(nullable = false)
    @Schema(description = "Comentario o texto de la reseña", example = "Muy buen producto, recomendado.", required = true)
    private String comentario;

    @Column(name = "fecha_creacion")
    @Schema(description = "Fecha en que se creó la reseña", example = "2025-07-08T15:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fechaCreacion;

    @PrePersist
    public void asignarFecha() {
        this.fechaCreacion = LocalDateTime.now();
    }
}