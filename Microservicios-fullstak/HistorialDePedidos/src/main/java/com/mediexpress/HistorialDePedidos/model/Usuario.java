package com.mediexpress.HistorialDePedidos.model;
import lombok.Data;
@Data
public class Usuario {
    private Long id;
    private String nombre;
    private String correo;
    private String rol;
    private boolean activo;
}
