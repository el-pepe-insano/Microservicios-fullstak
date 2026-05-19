package com.example.formulario.formulario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.formulario.formulario.model.Formulario;
import com.example.formulario.formulario.service.FormularioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;



@RestController
@RequestMapping("/api/v1/formulario")
@Tag(name = "Formulario", description = "API para gestión de formularios de consultas y problemas de productos")
public class FormularioController {

    @Autowired
    private FormularioService formularioService;

    @Operation(summary = "Obtener todos los formularios")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de formularios obtenida"),
        @ApiResponse(responseCode = "204", description = "No hay formularios disponibles")
    })
    @GetMapping
    public ResponseEntity<List<Formulario>> obtenerFormularios() {
        List<Formulario> formularios = formularioService.getFormularios();
        if (formularios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(formularios);
    }

    @Operation(summary = "Obtener un formulario por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Formulario encontrado"),
        @ApiResponse(responseCode = "404", description = "Formulario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Formulario> obtenerFormularioPorId(@PathVariable Long id) {
        try {
            Formulario formulario = formularioService.getFormulario(id);
            return ResponseEntity.ok(formulario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear un nuevo formulario")
    @ApiResponse(responseCode = "201", description = "Formulario creado exitosamente")
    @PostMapping
    public ResponseEntity<Formulario> crearFormulario(@RequestBody Formulario formulario) {
        Formulario nuevoFormulario = formularioService.saveFormulario(
            formulario.getConsultarProducto(),
            formulario.getProblemasProducto()
        );
        return ResponseEntity.status(201).body(nuevoFormulario);
    }

    @Operation(summary = "Eliminar un formulario por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Formulario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Formulario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFormulario(@PathVariable Long id) {
        try {
            formularioService.deleteFormulario(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}