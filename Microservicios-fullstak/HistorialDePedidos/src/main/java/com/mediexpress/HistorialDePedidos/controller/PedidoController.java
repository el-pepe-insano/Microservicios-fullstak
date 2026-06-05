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

@RestController
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Pedidos", description = "Gestión del historial de pedidos - MediExpress")
public class PedidoController {
    private final PedidoService pedidoService;
    private final UsuarioClienteService usuarioClienteService;

    public PedidoController(PedidoService pedidoService, UsuarioClienteService usuarioClienteService) {
        this.pedidoService = pedidoService;
        this.usuarioClienteService = usuarioClienteService;
    }

    @Operation(summary = "Listar todos los pedidos — ADMIN/OPERADOR")
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        List<Pedido> pedidos = pedidoService.listarTodos();
        return pedidos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Buscar pedido por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(
            @Parameter(description = "ID del pedido", example = "1") @PathVariable Long id) {
        try {
            return pedidoService.obtenerPorId(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Buscar pedidos por cliente — ADMIN/OPERADOR/CLIENTE")
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<?> obtenerPorCliente(@PathVariable Long idCliente) {
        List<Pedido> pedidos = pedidoService.buscarPorCliente(idCliente);
        return pedidos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Buscar pedidos por estado — ADMIN/OPERADOR")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> obtenerPorEstado(
            @Parameter(description = "Estado: PENDIENTE, EN_PROCESO, ENVIADO, ENTREGADO, CANCELADO")
            @PathVariable String estado) {
        List<Pedido> pedidos = pedidoService.buscarPorEstado(estado.toUpperCase());
        return pedidos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Crear un nuevo pedido — ADMIN/CLIENTE")
    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody Pedido pedido) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.guardar(pedido));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Actualizar estado del pedido — ADMIN/OPERADOR")
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(
            @PathVariable Long id,
            @Parameter(description = "Nuevo estado del pedido") @RequestParam String estado) {
        try {
            return ResponseEntity.ok(pedidoService.actualizarEstado(id, estado.toUpperCase()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Actualizar datos del pedido — ADMIN/OPERADOR")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        try {
            return ResponseEntity.ok(pedidoService.actualizar(id, pedido));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar pedido por ID — solo ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPedido(@PathVariable Long id) {
        try {
            pedidoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Consultar datos de un usuario desde Usuarios — validación cruzada")
    @GetMapping("/ver-usuario/{id}")
    public ResponseEntity<?> verUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioClienteService.obtenerUsuarioPorId(id).block();
        if (usuario == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(usuario);
    }
}
