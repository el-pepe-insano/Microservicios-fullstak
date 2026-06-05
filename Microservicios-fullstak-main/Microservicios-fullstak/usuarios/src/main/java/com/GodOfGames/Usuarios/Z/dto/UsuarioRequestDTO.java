package com.GodOfGames.Usuarios.Z.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
@Schema(description = "DTO para registro o actualización de usuario")
public class UsuarioRequestDTO {
    @NotBlank(message = "El nombre es obligatorio.")
    @Schema(example = "Juan Pérez")
    private String nombre;

    @NotBlank @Email(message = "El correo no es válido.")
    @Schema(example = "juan@mediexpress.cl")
    private String correo;

    @NotBlank @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
    @Schema(example = "password123")
    private String contraseña;

    @Schema(description = "Rol: ADMIN, CLIENTE, OPERADOR", example = "CLIENTE")
    private String rol;
}
