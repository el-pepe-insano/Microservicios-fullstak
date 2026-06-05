package com.example.ConsultarInventario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ConsultarInventario.model.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto, Long> {
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByCantidadGreaterThan(int cantidadMinima);

}
