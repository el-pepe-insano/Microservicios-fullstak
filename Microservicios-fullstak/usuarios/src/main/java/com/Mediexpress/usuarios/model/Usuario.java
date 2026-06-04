package com.Mediexpress.usuarios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del usuario", example = "1")
    private Long idUsuario;

    @Column(length = 100, nullable = false)
    @Schema(description = "Nombre del usuario", example = "Juan Perez")
    private String nombreUsuario;

    @Column(nullable = false, length = 12)
    @Schema(description = "Contraseña del usuario", example = "12345678")
    private String contraseña;

    @Column(nullable = false)
    @Schema(description = "Correo electrónico del usuario", example = "juan@mail.com")
    private String correo;

    @Schema(description = "Disponibilidad del usuario", example = "true")
    private boolean disponible;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol", nullable = false)
    @Schema(description = "Rol del usuario")
    private Rol rol;
}
