package com.example.formulario.formulario.model;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Formulario")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un formulario para consultas o problemas de productos")
public class Formulario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del formulario", example = "1")
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    @Schema(description = "Nombre o código del producto consultado", example = "Paracetamol 500mg")
    private String consultarProducto;

    @Column(nullable = false, length = 100)
    @Schema(description = "Descripción del problema o consulta sobre el producto", example = "No puedo encontrar el producto en la farmacia")
    private String ProblemasProducto;
}


