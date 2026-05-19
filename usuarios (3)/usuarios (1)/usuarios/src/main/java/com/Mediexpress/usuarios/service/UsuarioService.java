package com.Mediexpress.usuarios.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Mediexpress.usuarios.model.Usuario;
import com.Mediexpress.usuarios.repository.usuarioRepository;
@Service

public class UsuarioService {
    @Autowired
    private usuarioRepository usuarioRepository;

    // Listar todos los usuarios
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Buscar usuario por ID
    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElse(null); // Si no existe, devuelve null
    }

    // Agregar nuevo usuario con validaciones básicas
    public Usuario agregarUsuario(Usuario usuario) {
        // Validar que nombre y email no estén vacíos
        if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().isEmpty()) {
            throw new RuntimeException("El nombre no puede estar vacío");
        }

        if (usuario.getCorreo() == null || usuario.getCorreo().isEmpty()) {
            throw new RuntimeException("El correo no puede estar vacío");
        }

        return usuarioRepository.save(usuario);
    }

    // Eliminar usuario
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario actualizarUsuario(Usuario usuario) {
        if (usuario.getIdUsuario() == null) {
            throw new RuntimeException("El ID del usuario es obligatorio para actualizar");
        }
        return usuarioRepository.save(usuario);
}
}