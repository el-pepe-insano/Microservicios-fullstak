package com.mediexpress.login.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mediexpress.login.model.UsuarioLogin;
import com.mediexpress.login.repository.UsuarioLoginRepository;

@Service
public class UsuarioLoginService {

    @Autowired
    private UsuarioLoginRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioLogin registrar(UsuarioLogin usuario) {
        // Encripta la contraseña antes de guardar
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        return repo.save(usuario);
    }

    public List<UsuarioLogin> listar() {
        return repo.findAll();
    }

    public Optional<UsuarioLogin> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public Optional<UsuarioLogin> login(String correo, String contraseña) {
        // Busca por correo, luego verifica contraseña con BCrypt
        return repo.findByCorreo(correo)
                .filter(u -> passwordEncoder.matches(contraseña, u.getContraseña()));
    }

    public UsuarioLogin actualizar(Long id, UsuarioLogin actualizado) {
        actualizado.setId(id);
        if (actualizado.getContraseña() != null && !actualizado.getContraseña().isEmpty()) {
            actualizado.setContraseña(passwordEncoder.encode(actualizado.getContraseña()));
        }
        return repo.save(actualizado);
    }
}