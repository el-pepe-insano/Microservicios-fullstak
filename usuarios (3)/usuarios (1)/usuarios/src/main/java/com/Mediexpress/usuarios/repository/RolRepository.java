package com.Mediexpress.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Mediexpress.usuarios.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Rol findByNombreRol(String nombreRol);
}
