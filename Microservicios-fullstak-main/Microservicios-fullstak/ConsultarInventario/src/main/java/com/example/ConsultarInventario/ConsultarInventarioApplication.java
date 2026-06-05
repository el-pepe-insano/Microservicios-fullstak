package com.example.ConsultarInventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

// Excluimos explícitamente el generador automático de usuarios y contraseñas por defecto
@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
public class ConsultarInventarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsultarInventarioApplication.class, args);
    }
}