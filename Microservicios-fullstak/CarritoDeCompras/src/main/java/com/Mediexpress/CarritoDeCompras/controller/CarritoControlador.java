package com.Mediexpress.CarritoDeCompras.controller;

import java.util.List;

import com.Mediexpress.CarritoDeCompras.model.CarritoItem;
import com.Mediexpress.CarritoDeCompras.service.CarritoServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carrito")
@Tag(name = "Carrito de Compras", description = "Operaciones relacionadas con el carrito de compras del cliente")
public class CarritoControlador {

    @Autowired
    private CarritoServicio servicio;

    @Operation(summary = "Agregar un ítem al carrito")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Ítem agregado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error al agregar el ítem")
    })
    @PostMapping
    public ResponseEntity<?> agregar(@RequestBody CarritoItem item) {
        try {
            CarritoItem guardado = servicio.agregar(item);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Obtener los ítems del carrito por ID de cliente")
    @ApiResponse(responseCode = "200", description = "Ítems encontrados o lista vacía")
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<CarritoItem>> obtenerPorCliente(@PathVariable Long idCliente) {
        List<CarritoItem> items = servicio.obtenerPorCliente(idCliente);
        return ResponseEntity.ok(items);
    }

    @Operation(summary = "Obtener un ítem del carrito por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ítem encontrado"),
        @ApiResponse(responseCode = "404", description = "Ítem no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CarritoItem> obtenerPorId(@PathVariable Long id) {
        return servicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar un ítem del carrito")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ítem actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Error de validación o datos incorrectos"),
        @ApiResponse(responseCode = "404", description = "Ítem no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody CarritoItem item) {
        if (servicio.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        item.setId(id);
        try {
            CarritoItem actualizado = servicio.actualizar(item);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar un ítem del carrito por su ID")
    @ApiResponse(responseCode = "204", description = "Ítem eliminado correctamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Vaciar todo el carrito de un cliente")
    @ApiResponse(responseCode = "204", description = "Carrito del cliente vaciado correctamente")
    @DeleteMapping("/cliente/{idCliente}")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable Long idCliente) {
        servicio.eliminarPorCliente(idCliente);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener el total de productos en el carrito de un cliente")
    @ApiResponse(responseCode = "200", description = "Cantidad total obtenida")
    @GetMapping("/cliente/{idCliente}/total")
    public ResponseEntity<Integer> obtenerTotalCantidad(@PathVariable Long idCliente) {
        int total = servicio.obtenerTotalCantidad(idCliente);
        return ResponseEntity.ok(total);
    }
}