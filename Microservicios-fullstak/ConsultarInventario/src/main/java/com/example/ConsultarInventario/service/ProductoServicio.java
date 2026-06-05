package com.example.ConsultarInventario.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ConsultarInventario.model.Producto;
import com.example.ConsultarInventario.repository.ProductoRepositorio;
@Service
public class ProductoServicio {
    @Autowired private ProductoRepositorio productoRepository;
    public List<Producto> listarTodos() { return productoRepository.findAll(); }
    public Optional<Producto> buscarPorId(Long id) { return productoRepository.findById(id); }
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }
    public List<Producto> listarConStock() {
        return productoRepository.findByCantidadGreaterThan(0);
    }
    public List<Producto> listarStockBajo(int minimo) {
        return productoRepository.findByCantidadLessThanAndCantidadGreaterThan(minimo, 0);
    }
    public Producto guardar(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().isBlank())
            throw new RuntimeException("El nombre del producto es obligatorio.");
        if (producto.getPrecio() <= 0)
            throw new RuntimeException("El precio debe ser mayor a 0.");
        if (producto.getCantidad() < 0)
            throw new RuntimeException("La cantidad no puede ser negativa.");
        return productoRepository.save(producto);
    }
    public Producto actualizar(Long id, Producto producto) {
        if (!productoRepository.existsById(id))
            throw new RuntimeException("Producto con id " + id + " no existe.");
        producto.setId(id);
        return productoRepository.save(producto);
    }
    public Producto actualizarStock(Long id, int cantidad) {
        if (cantidad < 0) throw new RuntimeException("La cantidad no puede ser negativa.");
        return productoRepository.findById(id).map(p -> {
            p.setCantidad(cantidad);
            return productoRepository.save(p);
        }).orElseThrow(() -> new RuntimeException("Producto con id " + id + " no existe."));
    }
    public void eliminar(Long id) {
        if (!productoRepository.existsById(id))
            throw new RuntimeException("Producto con id " + id + " no existe.");
        productoRepository.deleteById(id);
    }
}
