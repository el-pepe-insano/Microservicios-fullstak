package com.GodOfGames.Usuarios.Z.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
@Schema(description = "DTO de respuesta del login con token JWT")
public class LoginResponseDTO {
    @Schema(example = "eyJhbGciOiJIUzI1NiJ9...")
    private String token;
    private UsuarioResponseDTO usuario;
}
