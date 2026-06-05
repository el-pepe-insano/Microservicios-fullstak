package com.GodOfGames.Usuarios.Z.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
@Schema(description = "DTO para inicio de sesión")
public class LoginRequestDTO {
    @NotBlank @Email
    @Schema(example = "admin@mediexpress.cl")
    private String correo;

    @NotBlank
    @Schema(example = "admin123")
    private String contraseña;
}
