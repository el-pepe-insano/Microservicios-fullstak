package com.mediexpress.HistorialDePedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediexpress.HistorialDePedidos.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {


}
