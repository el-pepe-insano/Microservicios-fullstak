package com.example.ConsultarInventario.controller;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ConsultarInventario.model.Producto;
import com.example.ConsultarInventario.service.ProductoServicio;
import io.swagger.v3.oas.annotations.Operation;
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
        try {
            return productoServicio.buscarPorId(id).map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @Operation(summary = "Buscar productos por nombre")
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNombre(@RequestParam String nombre) {
        List<Producto> p = productoServicio.buscarPorNombre(nombre);
        return p.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(p);
    }

    @Operation(summary = "Crear un nuevo producto")
    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto) {
        try { return ResponseEntity.status(HttpStatus.CREATED).body(productoServicio.guardar(producto)); }
        catch (RuntimeException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @Operation(summary = "Actualizar producto por ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        try { return ResponseEntity.ok(productoServicio.actualizar(id, producto)); }
        catch (RuntimeException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @Operation(summary = "Eliminar producto por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        try { productoServicio.eliminar(id); return ResponseEntity.noContent().build(); }
        catch (RuntimeException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }
}
