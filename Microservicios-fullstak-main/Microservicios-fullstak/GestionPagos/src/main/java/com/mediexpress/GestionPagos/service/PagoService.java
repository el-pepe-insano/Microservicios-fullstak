package com.mediexpress.GestionPagos.service;
import com.mediexpress.GestionPagos.model.*;
import com.mediexpress.GestionPagos.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class PagoService {
    @Autowired private PagoRepository repo;
    @Autowired private PedidoClienteService pedidoClienteService;

    public Pago procesarPago(Pago pago) {
        // Validacion cruzada: verificar que el pedido existe en HistorialDePedidos
        Pedido pedido = pedidoClienteService.obtenerPedido(pago.getIdPedido());
        if (pedido == null)
            throw new RuntimeException("El pedido con ID " + pago.getIdPedido() + " no existe.");

        // Validar que el cliente del pago coincide con el del pedido
        if (!pedido.getIdCliente().equals(pago.getIdCliente()))
            throw new RuntimeException("El cliente no coincide con el propietario del pedido.");

        // Validar monto
        if (pago.getMonto() == null || pago.getMonto() <= 0)
            throw new RuntimeException("El monto del pago debe ser mayor a 0.");

        if (!pago.getMonto().equals(pedido.getTotal()))
            throw new RuntimeException("El monto del pago (" + pago.getMonto() +
                    ") no coincide con el total del pedido (" + pedido.getTotal() + ").");

        // Verificar que el pedido no tenga ya un pago aprobado
        repo.findByIdPedidoAndEstado(pago.getIdPedido(), EstadoPago.APROBADO)
                .ifPresent(p -> { throw new RuntimeException("El pedido ya tiene un pago aprobado."); });

        pago.setEstado(EstadoPago.APROBADO);
        return repo.save(pago);
    }

    public List<Pago> listarTodos() { return repo.findAll(); }

    public Optional<Pago> buscarPorId(Long id) {
        if (id == null || id <= 0) throw new RuntimeException("El ID del pago no es válido.");
        return repo.findById(id);
    }

    public List<Pago> buscarPorCliente(Long idCliente) {
        return repo.findByIdCliente(idCliente);
    }

    public List<Pago> buscarPorPedido(Long idPedido) {
        return repo.findByIdPedido(idPedido);
    }

    public Pago anularPago(Long id) {
        Pago pago = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago con ID " + id + " no encontrado."));
        if (pago.getEstado() == EstadoPago.ANULADO)
            throw new RuntimeException("El pago ya está anulado.");
        pago.setEstado(EstadoPago.ANULADO);
        return repo.save(pago);
    }

    public void eliminar(Long id) {
        if (!repo.existsById(id)) throw new RuntimeException("Pago con ID " + id + " no existe.");
        repo.deleteById(id);
    }
}
