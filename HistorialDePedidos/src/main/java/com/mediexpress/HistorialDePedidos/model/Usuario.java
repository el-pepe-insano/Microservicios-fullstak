package com.mediexpress.HistorialDePedidos.model;

import lombok.Data;

@Data
public class Usuario {
    private Long idUsuario;
    private String nombreUsuario;
    private String contraseña;
    private String correo;
    private boolean disponible;
    private String rol;
}


