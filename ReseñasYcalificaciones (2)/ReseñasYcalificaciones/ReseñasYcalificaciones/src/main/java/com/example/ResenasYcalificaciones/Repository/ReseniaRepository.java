package com.example.ResenasYcalificaciones.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ResenasYcalificaciones.model.Resenia;

public interface ReseniaRepository extends JpaRepository<Resenia, Long> {
    List<Resenia> findByIdProducto(Long idProducto);

}
