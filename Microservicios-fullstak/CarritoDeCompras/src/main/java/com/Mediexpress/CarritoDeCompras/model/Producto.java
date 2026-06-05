ackage com.Mediexpress.CarritoDeCompras.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    private Long id;
    private String nombre;
    private String descripcion;
    private int cantidad; // Cambiado a int para que acepte el 150 de tu inventario
    private double precio; // Cambiado a double para que acepte el 2500.0 de tu inventario
}