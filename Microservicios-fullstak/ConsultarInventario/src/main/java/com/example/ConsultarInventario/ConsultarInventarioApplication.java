package com.example.ConsultarInventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Aquí es donde va la exclusión de seguridad, justo encima de la clase
@SpringBootApplication(exclude = { org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class })
public class ConsultarInventarioApplication {

    public static void main(String[] args) {
        // Esta es la línea mágica que enciende el microservicio, ¡no la podemos borrar!
        SpringApplication.run(ConsultarInventarioApplication.class, args);
    }

}