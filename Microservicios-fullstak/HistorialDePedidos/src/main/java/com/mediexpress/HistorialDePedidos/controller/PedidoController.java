package com.mediexpress.HistorialDePedidos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mediexpress.HistorialDePedidos.model.Pedido;
import com.mediexpress.HistorialDePedidos.model.Usuario;
import com.mediexpress.HistorialDePedidos.service.PedidoService;
import com.mediexpress.HistorialDePedidos.service.UsuarioClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Pedidos", description = "Operaciones relacionadas con los pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final UsuarioClienteService usuarioClienteService;

    public PedidoController(PedidoService pedidoService, UsuarioClienteService usuarioClienteService) {
        this.pedidoService = pedidoService;
        this.usuarioClienteService = usuarioClienteService;
    }

    @Operation(summary = "Listar todos los pedidos", description = "Obtiene todos los pedidos registrados")
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        try {
            List<Pedido> pedidos = pedidoService.listarTodos();
            return pedidos.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(pedidos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Obtener pedido por ID", description = "Obtiene un pedido por su ID único")
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(
        @Parameter(description = "ID del pedido a obtener", example = "1") @PathVariable Long id) {
        try {
            return pedidoService.obtenerPorId(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Crear un nuevo pedido", description = "Registra un nuevo pedido")
    @PostMapping
    public ResponseEntity<?> crearPedido(
        @Parameter(description = "Datos del pedido a crear") @RequestBody Pedido pedido) {
        try {
            Pedido nuevo = pedidoService.guardar(pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Actualizar un pedido existente", description = "Actualiza los datos de un pedido por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPedido(
        @Parameter(description = "ID del pedido a actualizar", example = "1") @PathVariable Long id,
        @Parameter(description = "Datos actualizados del pedido") @RequestBody Pedido pedido) {
        try {
            Pedido actualizado = pedidoService.actualizar(id, pedido);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar un pedido", description = "Elimina un pedido por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPedido(
        @Parameter(description = "ID del pedido a eliminar", example = "1") @PathVariable Long id) {
        try {
            pedidoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Obtener usuario desde microservicio de usuarios", description = "Consulta datos del usuario por su ID")
    @GetMapping("/ver-usuario/{id}")
    public Mono<Usuario> verUsuario(@PathVariable Long id) {
        return usuarioClienteService.obtenerUsuarioPorId(id);
    }
}