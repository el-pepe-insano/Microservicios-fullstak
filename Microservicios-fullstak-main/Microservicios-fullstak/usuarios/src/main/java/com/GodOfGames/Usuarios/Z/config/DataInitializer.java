package com.GodOfGames.Usuarios.Z.config;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.GodOfGames.Usuarios.Z.models.Rol;
import com.GodOfGames.Usuarios.Z.models.Usuario;
import com.GodOfGames.Usuarios.Z.repositories.UsuarioRepository;
@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner initData(UsuarioRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(Usuario.builder().nombre("Administrador MediExpress").correo("admin@mediexpress.cl")
                        .contraseña(encoder.encode("admin123")).rol(Rol.ADMIN).activo(true).build());
                repo.save(Usuario.builder().nombre("Cliente Demo").correo("cliente@mediexpress.cl")
                        .contraseña(encoder.encode("cliente123")).rol(Rol.CLIENTE).activo(true).build());
                repo.save(Usuario.builder().nombre("Operador Demo").correo("operador@mediexpress.cl")
                        .contraseña(encoder.encode("operador123")).rol(Rol.OPERADOR).activo(true).build());
                System.out.println("Usuarios iniciales cargados: ADMIN, CLIENTE, OPERADOR");
            }
        };
    }
}
