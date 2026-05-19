package com.example.ResenasYcalificaciones.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ResenasYcalificaciones.Repository.ReseniaRepository;
import com.example.ResenasYcalificaciones.model.Resenia;
@Service
public class ReseniaServicio {
    @Autowired
    private ReseniaRepository repositorio;

    // Guardar nueva reseña con validaciones básicas
    public Resenia guardar(Resenia resenia) {
        if (resenia.getIdProducto() == null || resenia.getIdProducto() <= 0) {
            throw new RuntimeException("El ID del producto es obligatorio y debe ser válido.");
        }

        if (resenia.getIdCliente() == null || resenia.getIdCliente() <= 0) {
            throw new RuntimeException("El ID del cliente es obligatorio y debe ser válido.");
        }

        if (resenia.getCalificacion() < 1 || resenia.getCalificacion() > 5) {
            throw new RuntimeException("La calificación debe estar entre 1 y 5.");
        }

        if (resenia.getComentario() == null || resenia.getComentario().trim().isEmpty()) {
            throw new RuntimeException("El comentario no puede estar vacío.");
        }

        return repositorio.save(resenia);
    }

    // Buscar reseñas por ID de producto
    public List<Resenia> buscarPorProducto(Long idProducto) {
        if (idProducto == null || idProducto <= 0) {
            throw new RuntimeException("El ID del producto debe ser válido.");
        }
        return repositorio.findByIdProducto(idProducto);
    }

    // Listar todas las reseñas
    public List<Resenia> buscarTodas() {
        return repositorio.findAll();
    }

    // Eliminar una reseña por ID
    public void eliminar(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("El ID de la reseña debe ser válido.");
        }
        if (!repositorio.existsById(id)) {
            throw new RuntimeException("No se encontró una reseña con el ID especificado.");
        }
        repositorio.deleteById(id);
    }
}
