package com.Mediexpress.usuarios.model;

import lombok.Data;

@Data
public class UsuarioLogin {
        private Long id;
    private String nombre;
    private String correo;
    private String contraseña;
    private boolean activo;
}


