package com.mediexpress.login.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mediexpress.login.model.UsuarioLogin;

public interface UsuarioLoginRepository extends JpaRepository<UsuarioLogin, Long> {
    Optional<UsuarioLogin> findByCorreo(String correo);
}