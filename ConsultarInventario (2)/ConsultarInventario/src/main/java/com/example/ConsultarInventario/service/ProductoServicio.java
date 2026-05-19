package com.example.ConsultarInventario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ConsultarInventario.model.Producto;
import com.example.ConsultarInventario.repository.ProductoRepositorio;
@Service
public class ProductoServicio {
    
     @Autowired
    private ProductoRepositorio productoRepository;

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }


    // Guardar nuevo producto
    public Producto guardar(Producto producto) {
        // Aquí puedes validar datos antes de guardar si quieres
        return productoRepository.save(producto);
    }
    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto con id " + id + " no existe.");
        }
        productoRepository.deleteById(id);
    }
}


    


        
