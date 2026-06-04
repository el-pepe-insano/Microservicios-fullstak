
package com.Mediexpress.CarritoDeCompras.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Mediexpress.CarritoDeCompras.model.CarritoItem;
import com.Mediexpress.CarritoDeCompras.model.Producto;
import com.Mediexpress.CarritoDeCompras.repository.CarritoRepositorio;

@Service
public class CarritoServicio {

    @Autowired
    private CarritoRepositorio repositorio;

    @Autowired
    private InventarioClienteService inventarioClienteService;

    public CarritoItem agregar(CarritoItem item) {
        if (item.getCantidad() <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0.");
        }
        if (item.getCantidad() > 100) {
            throw new RuntimeException("La cantidad no puede ser mayor a 100.");
        }

        // Validación cruzada: verificar que el producto existe en Inventario
        Producto producto = inventarioClienteService.obtenerProducto(item.getIdProducto());
        if (producto == null) {
            throw new RuntimeException("El producto con ID " + item.getIdProducto()
                    + " no existe en el inventario.");
        }
        if (producto.getCantidad() < item.getCantidad()) {
            throw new RuntimeException("Stock insuficiente. Disponible: "
                    + producto.getCantidad());
        }

        return repositorio.save(item);
    }

    public List<CarritoItem> obtenerPorCliente(Long idCliente) {
        return repositorio.findByIdCliente(idCliente);
    }

    public Optional<CarritoItem> obtenerPorId(Long id) {
        return repositorio.findById(id);
    }

    public CarritoItem actualizar(CarritoItem item) {
        if (item.getCantidad() > 100) {
            throw new RuntimeException("La cantidad no puede ser mayor a 100.");
        }
        return repositorio.save(item);
    }

    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }

    public void eliminarPorCliente(Long idCliente) {
        List<CarritoItem> items = repositorio.findByIdCliente(idCliente);
        repositorio.deleteAll(items);
    }

    public int obtenerTotalCantidad(Long idCliente) {
        List<CarritoItem> items = repositorio.findByIdCliente(idCliente);
        return items.stream().mapToInt(CarritoItem::getCantidad).sum();
    }
}