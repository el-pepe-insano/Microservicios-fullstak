package com.mediexpress.Despacho.service;
import com.mediexpress.Despacho.model.*;
import com.mediexpress.Despacho.repository.DespachoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class DespachoService {
    @Autowired private DespachoRepository repo;
    @Autowired private PedidoClienteService pedidoClienteService;

    public Despacho crearDespacho(Despacho despacho) {
        // Validacion cruzada: verificar que el pedido existe en HistorialDePedidos
        Pedido pedido = pedidoClienteService.obtenerPedido(despacho.getIdPedido());
        if (pedido == null)
            throw new RuntimeException("El pedido con ID " + despacho.getIdPedido() + " no existe.");

        // Verificar que el cliente coincide
        if (!pedido.getIdCliente().equals(despacho.getIdCliente()))
            throw new RuntimeException("El cliente no coincide con el propietario del pedido.");

        // Verificar que no existe ya un despacho para ese pedido
        repo.findByIdPedido(despacho.getIdPedido()).ifPresent(d -> {
            throw new RuntimeException("Ya existe un despacho para el pedido " + despacho.getIdPedido());
        });

        if (despacho.getDireccionEntrega() == null || despacho.getDireccionEntrega().isBlank())
            throw new RuntimeException("La dirección de entrega es obligatoria.");

        despacho.setFechaEntrega(LocalDateTime.now().plusDays(3));
        return repo.save(despacho);
    }

    public List<Despacho> listarTodos() { return repo.findAll(); }

    public Optional<Despacho> buscarPorId(Long id) {
        if (id == null || id <= 0) throw new RuntimeException("El ID del despacho no es válido.");
        return repo.findById(id);
    }

    public Optional<Despacho> buscarPorPedido(Long idPedido) {
        return repo.findByIdPedido(idPedido);
    }

    public List<Despacho> buscarPorCliente(Long idCliente) {
        return repo.findByIdCliente(idCliente);
    }

    public Optional<Despacho> buscarPorCodigo(String codigo) {
        return repo.findByCodigoSeguimiento(codigo);
    }

    public Despacho actualizarEstado(Long id, EstadoDespacho nuevoEstado) {
        Despacho despacho = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Despacho con ID " + id + " no encontrado."));
        if (despacho.getEstado() == EstadoDespacho.ENTREGADO)
            throw new RuntimeException("El despacho ya fue entregado, no se puede modificar.");
        if (despacho.getEstado() == EstadoDespacho.DEVUELTO)
            throw new RuntimeException("El despacho fue devuelto, no se puede modificar.");
        despacho.setEstado(nuevoEstado);
        if (nuevoEstado == EstadoDespacho.ENTREGADO)
            despacho.setFechaEntrega(LocalDateTime.now());
        return repo.save(despacho);
    }

    public void eliminar(Long id) {
        if (!repo.existsById(id)) throw new RuntimeException("Despacho con ID " + id + " no existe.");
        repo.deleteById(id);
    }
}
