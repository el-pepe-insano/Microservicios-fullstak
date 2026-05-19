package com.Mediexpress.CarritoDeCompras.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Mediexpress.CarritoDeCompras.model.CarritoItem;
import com.Mediexpress.CarritoDeCompras.repository.CarritoRepositorio;

@Service
public class CarritoServicio {
    @Autowired
    private CarritoRepositorio repositorio;

    // Agregar un nuevo item al carrito
    public CarritoItem agregar(CarritoItem item) {
        if (item.getCantidad() > 100) {
            throw new RuntimeException("La cantidad no puede ser mayor a 100.");
        }
        return repositorio.save(item);
    }

    // Obtener todos los ítems de un cliente
    public List<CarritoItem> obtenerPorCliente(Long idCliente) {
        return repositorio.findByIdCliente(idCliente);
    }

    // Obtener un ítem por ID
    public Optional<CarritoItem> obtenerPorId(Long id) {
        return repositorio.findById(id);
    }

    // Actualizar un ítem existente
    public CarritoItem actualizar(CarritoItem item) {
        if (item.getCantidad() > 100) {
            throw new RuntimeException("La cantidad no puede ser mayor a 100.");
        }
        return repositorio.save(item);
    }

    // Eliminar un ítem por ID
    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }

    // Vaciar el carrito completo de un cliente
    public void eliminarPorCliente(Long idCliente) {
        List<CarritoItem> items = repositorio.findByIdCliente(idCliente);
        repositorio.deleteAll(items);
    }

    // Calcular el total de productos en el carrito de un cliente
    public int obtenerTotalCantidad(Long idCliente) {
        List<CarritoItem> items = repositorio.findByIdCliente(idCliente);
        return items.stream().mapToInt(CarritoItem::getCantidad).sum();
    }

}


