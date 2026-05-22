package com.example.ResenasYcalificaciones.util;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    @Value("${jwt.secret}") private String secret;
    private SecretKey getKey() { return Keys.hmacShaKeyFor(secret.getBytes()); }
    public String obtenerCorreo(String token) {
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }
    public String obtenerRol(String token) {
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload().get("rol", String.class);
    }
    public boolean validarToken(String token) {
        try { Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token); return true; }
        catch (Exception e) { return false; }
    }
}
