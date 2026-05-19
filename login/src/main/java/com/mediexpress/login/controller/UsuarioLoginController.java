package com.mediexpress.login.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mediexpress.login.model.UsuarioLogin;
import com.mediexpress.login.service.UsuarioLoginService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/login")
@Tag(name = "Usuarios Login", description = "API para gestión de usuarios de login")
public class UsuarioLoginController {
    @Autowired
    private UsuarioLoginService service;

    @Operation(summary = "Registrar un nuevo usuario",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Usuario registrado correctamente"),
                   @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
               })
    @PostMapping("/registrar")
    public ResponseEntity<UsuarioLogin> registrar(@RequestBody UsuarioLogin usuario) {
        return ResponseEntity.ok(service.registrar(usuario));
    }

    @Operation(summary = "Iniciar sesión con correo y contraseña",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
                   @ApiResponse(responseCode = "401", description = "Credenciales incorrectas", content = @Content)
               })
    @PostMapping("/iniciar")
    public ResponseEntity<?> login(
        @Parameter(description = "Correo del usuario", example = "usuario@ejemplo.com")
        @RequestParam String correo,
        @Parameter(description = "Contraseña del usuario", example = "123456")
        @RequestParam String contraseña) {
        
        return service.login(correo, contraseña)
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas"));
    }

    @Operation(summary = "Listar todos los usuarios")
    @GetMapping
    public ResponseEntity<List<UsuarioLogin>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Actualizar un usuario por ID",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
                   @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
               })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioLogin> actualizar(
        @Parameter(description = "ID del usuario a actualizar", example = "1")
        @PathVariable Long id,
        @RequestBody UsuarioLogin usuario) {
        
        return ResponseEntity.ok(service.actualizar(id, usuario));
    }

    @Operation(summary = "Eliminar un usuario por ID",
               responses = {
                   @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
                   @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
               })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
        @Parameter(description = "ID del usuario a eliminar", example = "1")
        @PathVariable Long id) {
        
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
}