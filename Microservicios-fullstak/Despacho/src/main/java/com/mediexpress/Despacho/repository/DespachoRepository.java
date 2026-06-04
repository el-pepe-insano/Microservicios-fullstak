package com.mediexpress.Despacho.repository;
import com.mediexpress.Despacho.model.Despacho;
import com.mediexpress.Despacho.model.EstadoDespacho;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface DespachoRepository extends JpaRepository<Despacho, Long> {
    Optional<Despacho> findByIdPedido(Long idPedido);
    List<Despacho> findByIdCliente(Long idCliente);
    List<Despacho> findByEstado(EstadoDespacho estado);
    Optional<Despacho> findByCodigoSeguimiento(String codigo);
}
