package com.example.ResenasYcalificaciones.model;

import lombok.Data;

@Data
public class Usuario {
    private Long idUsuario;
    private String nombreUsuario;
    private String correo;
    private String rol;
    // getters y setters
}


