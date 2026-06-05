package com.example.ResenasYcalificaciones.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ResenasYcalificaciones.Service.ReseniaServicio;
import com.example.ResenasYcalificaciones.model.Resenia;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Reseñas", description = "Gestión de reseñas y calificaciones de productos - MediExpress")
@RestController
@RequestMapping("/resenias")
public class ReseniaController {
    @Autowired private ReseniaServicio servicio;

    @Operation(summary = "Crear una nueva reseña — ADMIN/CLIENTE")
    @PostMapping
    public ResponseEntity<?> crearResenia(@RequestBody Resenia resenia) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(servicio.guardar(resenia));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Listar todas las reseñas — ADMIN/OPERADOR/CLIENTE")
    @GetMapping
    public ResponseEntity<?> listarTodas() {
        List<Resenia> todas = servicio.buscarTodas();
        return todas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(todas);
    }

    @Operation(summary = "Obtener reseñas de un producto")
    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<?> obtenerPorProducto(
            @Parameter(description = "ID del producto") @PathVariable Long idProducto) {
        try {
            List<Resenia> lista = servicio.buscarPorProducto(idProducto);
            return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Obtener reseñas de un cliente")
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<?> obtenerPorCliente(@PathVariable Long idCliente) {
        List<Resenia> lista = servicio.buscarPorCliente(idCliente);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener promedio de calificaciones de un producto")
    @GetMapping("/producto/{idProducto}/promedio")
    public ResponseEntity<?> obtenerPromedio(@PathVariable Long idProducto) {
        try {
            double promedio = servicio.calcularPromedio(idProducto);
            return ResponseEntity.ok(java.util.Map.of("idProducto", idProducto, "promedio", promedio));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Verificar si usuario existe — validación cruzada con Usuarios")
    @GetMapping("/verificar-usuario/{id}")
    public ResponseEntity<?> verificarUsuario(@PathVariable Long id) {
        try {
            boolean existe = servicio.verificarUsuario(id);
            return ResponseEntity.ok(java.util.Map.of("idUsuario", id, "existe", existe));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar una reseña por ID — solo ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarResenia(
            @Parameter(description = "ID de la reseña") @PathVariable Long id) {
        try {
            servicio.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
