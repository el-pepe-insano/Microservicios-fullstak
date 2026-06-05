package com.example.ConsultarInventario.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ConsultarInventario.model.Producto;
import com.example.ConsultarInventario.service.ProductoServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/productos")
@Tag(name = "Productos", description = "Gestión de productos e inventario - MediExpress")
public class ProductoControlador {
    @Autowired private ProductoServicio productoServicio;

    @Operation(summary = "Listar todos los productos")
    @GetMapping
    public ResponseEntity<List<Producto>> listarTodos() {
        List<Producto> p = productoServicio.listarTodos();
        return p.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(p);
    }

    @Operation(summary = "Buscar producto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return productoServicio.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar productos por nombre")
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNombre(
            @Parameter(description = "Nombre o parte del nombre") @RequestParam String nombre) {
        List<Producto> p = productoServicio.buscarPorNombre(nombre);
        return p.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(p);
    }

    @Operation(summary = "Listar productos con stock disponible (cantidad > 0)")
    @GetMapping("/disponibles")
    public ResponseEntity<?> listarDisponibles() {
        List<Producto> p = productoServicio.listarConStock();
        return p.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(p);
    }

    @Operation(summary = "Listar productos con stock bajo — ADMIN/OPERADOR")
    @GetMapping("/stock-bajo")
    public ResponseEntity<?> listarStockBajo(
            @Parameter(description = "Cantidad mínima de alerta (por defecto 10)")
            @RequestParam(defaultValue = "10") int minimo) {
        List<Producto> p = productoServicio.listarStockBajo(minimo);
        return p.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(p);
    }

    @Operation(summary = "Crear un nuevo producto — ADMIN/OPERADOR")
    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto) {
        try { return ResponseEntity.status(HttpStatus.CREATED).body(productoServicio.guardar(producto)); }
        catch (RuntimeException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @Operation(summary = "Actualizar producto por ID — ADMIN/OPERADOR")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        try { return ResponseEntity.ok(productoServicio.actualizar(id, producto)); }
        catch (RuntimeException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @Operation(summary = "Actualizar solo el stock de un producto — ADMIN/OPERADOR")
    @PatchMapping("/{id}/stock")
    public ResponseEntity<?> actualizarStock(
            @PathVariable Long id,
            @Parameter(description = "Nueva cantidad en inventario") @RequestParam int cantidad) {
        try { return ResponseEntity.ok(productoServicio.actualizarStock(id, cantidad)); }
        catch (RuntimeException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @Operation(summary = "Eliminar producto por ID — solo ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        try { productoServicio.eliminar(id); return ResponseEntity.noContent().build(); }
        catch (RuntimeException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }
}
