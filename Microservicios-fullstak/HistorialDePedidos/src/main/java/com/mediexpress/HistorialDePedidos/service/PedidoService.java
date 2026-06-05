package com.mediexpress.HistorialDePedidos.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mediexpress.HistorialDePedidos.model.Pedido;
import com.mediexpress.HistorialDePedidos.model.Usuario;
import com.mediexpress.HistorialDePedidos.repository.PedidoRepository;
@Service
public class PedidoService {
    @Autowired private PedidoRepository repo;
    @Autowired private UsuarioClienteService usuarioClienteService;

    public List<Pedido> listarTodos() { return repo.findAll(); }

    public Optional<Pedido> obtenerPorId(Long id) {
        if (id == null || id <= 0) throw new RuntimeException("El ID del pedido no es válido.");
        return repo.findById(id);
    }

    public List<Pedido> buscarPorCliente(Long idCliente) {
        return repo.findByIdCliente(idCliente);
    }

    public List<Pedido> buscarPorEstado(String estado) {
        return repo.findByEstado(estado);
    }

    public Pedido guardar(Pedido pedido) {
        Usuario usuario = usuarioClienteService.obtenerUsuarioPorId(pedido.getIdCliente()).block();
        if (usuario == null)
            throw new RuntimeException("El usuario con ID " + pedido.getIdCliente() + " no existe.");
        if (pedido.getTotal() == null || pedido.getTotal() <= 0)
            throw new RuntimeException("El total del pedido debe ser mayor a 0.");
        if (pedido.getEstado() == null || pedido.getEstado().isBlank())
            pedido.setEstado("PENDIENTE");
        return repo.save(pedido);
    }

    public Pedido actualizarEstado(Long id, String estado) {
        return repo.findById(id).map(p -> {
            p.setEstado(estado);
            return repo.save(p);
        }).orElseThrow(() -> new RuntimeException("Pedido con ID " + id + " no encontrado."));
    }

    public Pedido actualizar(Long id, Pedido pedido) {
        if (!repo.existsById(id)) throw new RuntimeException("El pedido con ID " + id + " no existe.");
        pedido.setId(id);
        return repo.save(pedido);
    }

    public void eliminar(Long id) {
        if (!repo.existsById(id)) throw new RuntimeException("El pedido con ID " + id + " no existe.");
        repo.deleteById(id);
    }
}
