package com.GodOfGames.Usuarios.Z.models;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
@Entity @Table(name = "usuarios") @Data @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Entidad que representa un usuario del sistema MediExpress")
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El nombre es obligatorio.") @Column(nullable = false)
    private String nombre;
    @NotBlank @Email @Column(nullable = false, unique = true)
    private String correo;
    @NotBlank @Size(min = 6) @Column(nullable = false)
    private String contraseña;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    @Schema(description = "Rol: ADMIN, CLIENTE, OPERADOR", example = "CLIENTE")
    private Rol rol;
    @Column(nullable = false)
    private boolean activo = true;
}
