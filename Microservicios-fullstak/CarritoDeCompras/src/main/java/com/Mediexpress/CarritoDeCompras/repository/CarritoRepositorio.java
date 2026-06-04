package com.Mediexpress.CarritoDeCompras.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Mediexpress.CarritoDeCompras.model.CarritoItem;

public interface CarritoRepositorio extends JpaRepository<CarritoItem, Long> {
    List<CarritoItem> findByIdCliente(Long idCliente); 
}


