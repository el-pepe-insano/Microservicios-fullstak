package com.mediexpress.HistorialDePedidos.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediexpress.HistorialDePedidos.model.Pedido;
import com.mediexpress.HistorialDePedidos.repository.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    // Listar todos los pedidos
    public List<Pedido> listarTodos() {
        return repo.findAll();
    }

    // Obtener pedido por ID con validación
    public Optional<Pedido> obtenerPorId(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("El ID del pedido no es válido.");
        }
        return repo.findById(id);
    }

    // Crear nuevo pedido
    public Pedido guardar(Pedido pedido) {
        return repo.save(pedido);
    }

    // Actualizar pedido (asegura que se actualice el pedido correcto)
    public Pedido actualizar(Long id, Pedido pedido) {
        pedido.setId(id);
        return repo.save(pedido);
    }

    // Eliminar pedido con validación
    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("El pedido con ID " + id + " no existe.");
        }
        repo.deleteById(id);
    }
}