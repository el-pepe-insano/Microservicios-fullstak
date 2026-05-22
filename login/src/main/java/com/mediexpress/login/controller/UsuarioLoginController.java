package com.mediexpress.login.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mediexpress.login.model.UsuarioLogin;
import com.mediexpress.login.service.UsuarioLoginService;
import com.mediexpress.login.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/login")
@Tag(name = "Login", description = "Autenticación y gestión de usuarios")
public class UsuarioLoginController {

    @Autowired
    private UsuarioLoginService service;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "Registrar un nuevo usuario")
    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody UsuarioLogin usuario) {
        try {
            return ResponseEntity.ok(service.registrar(usuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Iniciar sesión — devuelve token JWT")
    @PostMapping("/iniciar")
    public ResponseEntity<?> login(
            @Parameter(description = "Correo del usuario") @RequestParam String correo,
            @Parameter(description = "Contraseña del usuario") @RequestParam String contraseña) {

        return service.login(correo, contraseña)
                .map(usuario -> {
                    String token = jwtUtil.generarToken(usuario.getCorreo(), usuario.getRol());
                    Map<String, Object> respuesta = new HashMap<>();
                    respuesta.put("token", token);
                    respuesta.put("usuario", usuario);
                    return ResponseEntity.ok(respuesta);
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Credenciales incorrectas")));
    }

    @Operation(summary = "Listar todos los usuarios")
    @GetMapping
    public ResponseEntity<List<UsuarioLogin>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Actualizar usuario por ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody UsuarioLogin usuario) {
        try {
            return ResponseEntity.ok(service.actualizar(id, usuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar usuario por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}