package com.example.ResenasYcalificaciones.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ResenasYcalificaciones.Repository.ReseniaRepository;
import com.example.ResenasYcalificaciones.model.Resenia;
import com.example.ResenasYcalificaciones.model.Usuario;

@Service
public class ReseniaServicio {

    @Autowired
    private ReseniaRepository repositorio;

    @Autowired
    private UsuarioClienteService usuarioClienteService;

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

        // Validación cruzada: verificar que el usuario existe en microservicio Usuarios
        Usuario usuario = usuarioClienteService
                .obtenerUsuarioPorId(resenia.getIdCliente()).block();
        if (usuario == null) {
            throw new RuntimeException("El usuario con ID " + resenia.getIdCliente()
                    + " no existe.");
        }

        return repositorio.save(resenia);
    }

    public List<Resenia> buscarPorProducto(Long idProducto) {
        if (idProducto == null || idProducto <= 0) {
            throw new RuntimeException("El ID del producto debe ser válido.");
        }
        return repositorio.findByIdProducto(idProducto);
    }

    public List<Resenia> buscarTodas() {
        return repositorio.findAll();
    }

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