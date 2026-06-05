package com.mediexpress.Despacho.model;
import lombok.Data;
import java.time.LocalDate;
@Data
public class Pedido {
    private Long id;
    private Long idCliente;
    private LocalDate fecha;
    private Double total;
    private String estado;
}
