package com.mediexpress.GestionPagos.repository;
import com.mediexpress.GestionPagos.model.EstadoPago;
import com.mediexpress.GestionPagos.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByIdCliente(Long idCliente);
    List<Pago> findByIdPedido(Long idPedido);
    Optional<Pago> findByIdPedidoAndEstado(Long idPedido, EstadoPago estado);
    List<Pago> findByEstado(EstadoPago estado);
}
