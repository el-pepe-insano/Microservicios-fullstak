package com.example.formulario.formulario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.formulario.formulario.model.Formulario;

@Repository
public interface FormularioRepository extends JpaRepository<Formulario,Long> {

}
