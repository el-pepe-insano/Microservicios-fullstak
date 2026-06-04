package com.Mediexpress.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Mediexpress.usuarios.model.Usuario;

@Repository
public interface usuarioRepository extends JpaRepository<Usuario,Long> {

}
