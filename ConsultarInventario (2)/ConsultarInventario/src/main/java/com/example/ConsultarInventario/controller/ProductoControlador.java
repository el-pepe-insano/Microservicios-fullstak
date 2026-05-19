package com.example.ConsultarInventario.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ConsultarInventario.model.Producto;
import com.example.ConsultarInventario.service.ProductoServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@RequestMapping("/productos")
@Tag(name = "Productos", description = "API para gestión de productos")
public class ProductoControlador {

    @Autowired
    private ProductoServicio productoServicio;

    @Operation(summary = "Listar todos los productos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida"),
        @ApiResponse(responseCode = "204", description = "No hay productos disponibles")
    })
    @GetMapping
    public ResponseEntity<List<Producto>> listarTodos() {
        List<Producto> productos = productoServicio.listarTodos();
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @Operation(summary = "Buscar producto por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Optional<Producto> producto = productoServicio.buscarPorId(id);
            return producto.map(ResponseEntity::ok)
                           .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Buscar productos por nombre")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Productos encontrados"),
        @ApiResponse(responseCode = "204", description = "No hay productos que coincidan"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNombre(@RequestParam String nombre) {
        try {
            List<Producto> productos = productoServicio.buscarPorNombre(nombre);
            if (productos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(productos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Crear un nuevo producto")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud")
    })
    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto) {
        try {
            Producto nuevo = productoServicio.guardar(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar producto por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        try {
            productoServicio.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}