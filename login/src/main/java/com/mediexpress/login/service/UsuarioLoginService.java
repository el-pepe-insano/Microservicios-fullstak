package com.mediexpress.login.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediexpress.login.model.UsuarioLogin;
import com.mediexpress.login.repository.UsuarioLoginRepository;
@Service
public class UsuarioLoginService {
    @Autowired
    private UsuarioLoginRepository repo;

    public UsuarioLogin registrar(UsuarioLogin usuario) {
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
        return repo.findByCorreoAndContraseña(correo, contraseña);
    }

    public UsuarioLogin actualizar(Long id, UsuarioLogin actualizado) {
        actualizado.setId(id);
        return repo.save(actualizado);
    }

}
