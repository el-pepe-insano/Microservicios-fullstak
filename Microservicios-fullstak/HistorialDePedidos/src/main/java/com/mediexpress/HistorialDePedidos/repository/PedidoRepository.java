package com.mediexpress.HistorialDePedidos.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mediexpress.HistorialDePedidos.model.Pedido;
import java.util.List;
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByIdCliente(Long idCliente);
    List<Pedido> findByEstado(String estado);
}
