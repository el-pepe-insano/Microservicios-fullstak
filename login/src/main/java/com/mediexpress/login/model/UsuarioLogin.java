package com.mediexpress.login.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "usuarios_login")
@Schema(description = "Entidad que representa un usuario para login")
public class UsuarioLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 100)
    private String correo;

    @Column(nullable = false, length = 100)
    @Schema(description = "Contraseña (se almacena encriptada con BCrypt)")
    private String contraseña;

    @Column(nullable = false, length = 30)
    @Schema(description = "Rol del usuario: ADMIN, CLIENTE, OPERADOR", example = "CLIENTE")
    private String rol;

    @Schema(description = "Estado activo del usuario", example = "true")
    private boolean activo;
}