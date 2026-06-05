package com.mediexpress.Despacho.controller;
import com.mediexpress.Despacho.model.Despacho;
import com.mediexpress.Despacho.model.EstadoDespacho;
import com.mediexpress.Despacho.service.DespachoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/v1/despachos")
@Tag(name = "Despacho", description = "Gestión de despachos y seguimiento de envíos - MediExpress")
public class DespachoController {
    @Autowired private DespachoService despachoService;

    @Operation(summary = "Crear un nuevo despacho para un pedido")
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Despacho despacho) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(despachoService.crearDespacho(despacho));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Listar todos los despachos")
    @GetMapping
    public ResponseEntity<List<Despacho>> listar() {
        return ResponseEntity.ok(despachoService.listarTodos());
    }

    @Operation(summary = "Buscar despacho por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return despachoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar despacho por ID de pedido")
    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<?> buscarPorPedido(@PathVariable Long idPedido) {
        return despachoService.buscarPorPedido(idPedido)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar despachos por cliente")
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Despacho>> buscarPorCliente(@PathVariable Long idCliente) {
        return ResponseEntity.ok(despachoService.buscarPorCliente(idCliente));
    }

    @Operation(summary = "Seguimiento por código")
    @GetMapping("/seguimiento/{codigo}")
    public ResponseEntity<?> buscarPorCodigo(
            @Parameter(description = "Código de seguimiento", example = "MED-00001")
            @PathVariable String codigo) {
        return despachoService.buscarPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar estado del despacho — OPERADOR o ADMIN")
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(
            @PathVariable Long id,
            @Parameter(description = "Nuevo estado: PREPARANDO, EN_CAMINO, ENTREGADO, DEVUELTO")
            @RequestParam EstadoDespacho estado) {
        try {
            return ResponseEntity.ok(despachoService.actualizarEstado(id, estado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar despacho — solo ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            despachoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
