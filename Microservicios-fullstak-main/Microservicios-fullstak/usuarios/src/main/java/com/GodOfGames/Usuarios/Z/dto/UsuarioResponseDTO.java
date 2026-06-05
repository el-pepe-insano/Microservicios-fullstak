package com.GodOfGames.Usuarios.Z.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
@Schema(description = "DTO de respuesta con datos del usuario (sin contraseña)")
public class UsuarioResponseDTO {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "Juan Pérez")
    private String nombre;
    @Schema(example = "juan@mediexpress.cl")
    private String correo;
    @Schema(example = "CLIENTE")
    private String rol;
    @Schema(example = "true")
    private boolean activo;
}
