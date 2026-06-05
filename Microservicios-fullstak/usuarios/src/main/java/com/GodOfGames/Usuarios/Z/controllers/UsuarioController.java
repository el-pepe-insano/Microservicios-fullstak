package com.GodOfGames.Usuarios.Z.controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.GodOfGames.Usuarios.Z.Service.UsuarioService;
import com.GodOfGames.Usuarios.Z.models.Rol;
import com.GodOfGames.Usuarios.Z.models.Usuario;
import com.GodOfGames.Usuarios.Z.security.JwtUtil;
import java.util.*;

@RestController @RequestMapping("/api/usuarios") @Slf4j
@Tag(name = "Usuarios", description = "Gestión de usuarios y autenticación - MediExpress")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public UsuarioController(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Registrar nuevo usuario — público")
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@Valid @RequestBody Usuario usuario) {
        try {
            Usuario nuevo = usuarioService.registrarUsuario(usuario);
            nuevo.setContraseña(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Iniciar sesión — devuelve token JWT")
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Parameter(description = "Correo del usuario") @RequestParam String correo,
            @Parameter(description = "Contraseña del usuario") @RequestParam String contraseña) {
        return usuarioService.login(correo, contraseña).map(u -> {
            String token = jwtUtil.generarToken(u.getCorreo(), u.getRol().name());
            u.setContraseña(null);
            Map<String, Object> r = new HashMap<>();
            r.put("token", token);
            r.put("usuario", u);
            return ResponseEntity.ok(r);
        }).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Credenciales incorrectas")));
    }

    @Operation(summary = "Listar todos los usuarios — ADMIN/OPERADOR/CLIENTE")
    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> lista = usuarioService.listarUsuarios();
        lista.forEach(u -> u.setContraseña(null));
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar usuario por ID — ADMIN/OPERADOR/CLIENTE")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(u -> { u.setContraseña(null); return ResponseEntity.ok(u); })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar usuarios por rol — ADMIN/OPERADOR")
    @GetMapping("/rol/{rol}")
    public ResponseEntity<?> buscarPorRol(
            @Parameter(description = "Rol: ADMIN, CLIENTE, OPERADOR") @PathVariable String rol) {
        try {
            Rol rolEnum = Rol.valueOf(rol.toUpperCase());
            List<Usuario> lista = usuarioService.buscarPorRol(rolEnum);
            lista.forEach(u -> u.setContraseña(null));
            return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Rol inválido. Use: ADMIN, CLIENTE, OPERADOR"));
        }
    }

    @Operation(summary = "Activar o desactivar usuario — ADMIN")
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(
            @PathVariable Long id,
            @Parameter(description = "true para activar, false para desactivar")
            @RequestParam boolean activo) {
        try {
            Usuario u = usuarioService.cambiarEstado(id, activo);
            u.setContraseña(null);
            return ResponseEntity.ok(u);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Actualizar usuario por ID — ADMIN/OPERADOR")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        try {
            Usuario a = usuarioService.actualizarUsuario(id, usuario);
            a.setContraseña(null);
            return ResponseEntity.ok(a);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Eliminar usuario por ID — solo ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
