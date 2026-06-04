package com.example.ConsultarInventario.security;

import com.example.ConsultarInventario.security.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired 
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
            
        String header = req.getHeader("Authorization");
        System.out.println("\n--- INICIO DE PETICION ---");
        System.out.println("1. Header recibido: " + (header != null ? "SI" : "NO"));
        
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            
            boolean esValido = jwtUtil.validarToken(token);
            System.out.println("2. ¿Token es válido (misma clave secreta y no vencido)?: " + esValido);
            
            if (esValido) {
                String correo = jwtUtil.obtenerCorreo(token);
                String rol = jwtUtil.obtenerRol(token);
                
                System.out.println("3. Correo extraído: " + correo);
                System.out.println("4. Rol extraído: " + rol);
                System.out.println("5. Rol que se le dará a Spring: ROLE_" + rol);
                
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    correo, 
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_" + rol))
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
                System.out.println("6. ¡Acceso concedido en el Filtro!");
            } else {
                System.out.println("X. ERROR: El token fue rechazado por JwtUtil (puede ser distinto SecretKey o estar vencido).");
            }
        } else {
            System.out.println("X. ERROR: No hay token en la petición o no empieza con Bearer.");
        }
        System.out.println("--- FIN DE PETICION ---\n");
        
        chain.doFilter(req, res);
    }
}