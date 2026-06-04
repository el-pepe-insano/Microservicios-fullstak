package com.GodOfGames.Usuarios.Z.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.GodOfGames.Usuarios.Z.models.Rol;
import com.GodOfGames.Usuarios.Z.models.Usuario;
import com.GodOfGames.Usuarios.Z.repositories.UsuarioRepository;
import java.util.List;
import java.util.Optional;
@Service @Slf4j
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public Usuario registrarUsuario(Usuario nuevoUsuario) {
        if (usuarioRepository.findByCorreo(nuevoUsuario.getCorreo()).isPresent())
            throw new RuntimeException("El correo ya está registrado.");
        if (nuevoUsuario.getRol() == null || nuevoUsuario.getRol() == Rol.ADMIN)
            nuevoUsuario.setRol(Rol.CLIENTE);
        nuevoUsuario.setContraseña(passwordEncoder.encode(nuevoUsuario.getContraseña()));
        nuevoUsuario.setActivo(true);
        return usuarioRepository.save(nuevoUsuario);
    }
    public Optional<Usuario> login(String correo, String contraseña) {
        return usuarioRepository.findByCorreo(correo)
                .filter(u -> passwordEncoder.matches(contraseña, u.getContraseña()));
    }
    public List<Usuario> listarUsuarios() { return usuarioRepository.findAll(); }
    public Optional<Usuario> buscarPorId(Long id) { return usuarioRepository.findById(id); }
    public Usuario actualizarUsuario(Long id, Usuario d) {
        return usuarioRepository.findById(id).map(u -> {
            u.setNombre(d.getNombre());
            if (d.getContraseña() != null && !d.getContraseña().isEmpty())
                u.setContraseña(passwordEncoder.encode(d.getContraseña()));
            if (d.getRol() != null && d.getRol() != Rol.ADMIN) u.setRol(d.getRol());
            u.setActivo(d.isActivo());
            return usuarioRepository.save(u);
        }).orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no encontrado."));
    }
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) throw new RuntimeException("Usuario con ID " + id + " no existe.");
        usuarioRepository.deleteById(id);
    }
}
