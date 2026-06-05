package com.mediexpress.GestionPagos.controller;
import com.mediexpress.GestionPagos.model.Pago;
import com.mediexpress.GestionPagos.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/v1/pagos")
@Tag(name = "Pagos", description = "Gestión de pagos de pedidos - MediExpress")
public class PagoController {
    @Autowired private PagoService pagoService;

    @Operation(summary = "Procesar un nuevo pago")
    @PostMapping
    public ResponseEntity<?> procesarPago(@RequestBody Pago pago) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.procesarPago(pago));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Listar todos los pagos")
    @GetMapping
    public ResponseEntity<List<Pago>> listar() {
        return ResponseEntity.ok(pagoService.listarTodos());
    }

    @Operation(summary = "Buscar pago por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(
            @Parameter(description = "ID del pago") @PathVariable Long id) {
        return pagoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar pagos por cliente")
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Pago>> buscarPorCliente(@PathVariable Long idCliente) {
        return ResponseEntity.ok(pagoService.buscarPorCliente(idCliente));
    }

    @Operation(summary = "Buscar pagos por pedido")
    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<List<Pago>> buscarPorPedido(@PathVariable Long idPedido) {
        return ResponseEntity.ok(pagoService.buscarPorPedido(idPedido));
    }

    @Operation(summary = "Anular un pago — solo ADMIN u OPERADOR")
    @PutMapping("/{id}/anular")
    public ResponseEntity<?> anular(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(pagoService.anularPago(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar un pago — solo ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            pagoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
