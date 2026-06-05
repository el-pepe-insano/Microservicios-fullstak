package com.example.ResenasYcalificaciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ResenasYcalificaciones.Service.ReseniaServicio;
import com.example.ResenasYcalificaciones.Service.UsuarioClienteService;
import com.example.ResenasYcalificaciones.model.Resenia;
import com.example.ResenasYcalificaciones.model.Usuario;
import reactor.core.publisher.Mono;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "Reseñas", description = "Operaciones relacionadas con reseñas de productos")
@RestController
@RequestMapping("/resenias")
public class ReseniaController {

    @Autowired
    private ReseniaServicio servicio;

    @Operation(summary = "Crear una nueva reseña")
    @PostMapping
    public ResponseEntity<?> crearResenia(@RequestBody Resenia resenia) {
        try {
            Resenia guardada = servicio.guardar(resenia);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Obtener reseñas por ID de producto")
    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<?> obtenerPorProducto(
        @Parameter(description = "ID del producto", required = true) @PathVariable Long idProducto) {
        try {
            List<Resenia> lista = servicio.buscarPorProducto(idProducto);
            if (lista.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lista);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Listar todas las reseñas")
    @GetMapping
    public ResponseEntity<List<Resenia>> listarTodas() {
        List<Resenia> todas = servicio.buscarTodas();
        if (todas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(todas);
    }

    @Operation(summary = "Eliminar una reseña por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarResenia(
        @Parameter(description = "ID de la reseña a eliminar", required = true) @PathVariable Long id) {
        try {
            servicio.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
      @Autowired
    private UsuarioClienteService usuarioClienteService;

    @GetMapping("/usuario/{id}")
public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
    Usuario usuario = usuarioClienteService.obtenerUsuarioPorId(id).block(); 
    if (usuario == null) {
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(usuario);
}
}