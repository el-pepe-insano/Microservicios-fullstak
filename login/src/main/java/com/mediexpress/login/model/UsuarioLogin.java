package com.mediexpress.login.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Schema(description = "ID único del usuario", example = "1")
    private Long id;

    @Column(nullable = false, length = 100)
    @Schema(description = "Nombre completo del usuario", example = "Diego Insano")
    private String nombre;

    @Column(nullable = false, unique = true, length = 100)
    @Schema(description = "Correo electrónico del usuario", example = "diego@example.com")
    private String correo;

    @Column(nullable = false, length = 100)
    @Schema(description = "Contraseña cifrada", example = "********")
    private String contraseña;

    @Schema(description = "Estado activo del usuario", example = "true")
    private boolean activo;
}
