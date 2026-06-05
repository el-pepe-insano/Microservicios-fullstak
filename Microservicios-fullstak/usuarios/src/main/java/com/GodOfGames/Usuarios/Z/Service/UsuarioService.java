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
    public UsuarioService(UsuarioRepository repo, PasswordEncoder encoder) {
        this.usuarioRepository = repo; this.passwordEncoder = encoder;
    }
    public Usuario registrarUsuario(Usuario u) {
        if (usuarioRepository.findByCorreo(u.getCorreo()).isPresent())
            throw new RuntimeException("El correo ya está registrado.");
        if (u.getRol() == null) u.setRol(Rol.CLIENTE);
        u.setContraseña(passwordEncoder.encode(u.getContraseña()));
        u.setActivo(true);
        return usuarioRepository.save(u);
    }
    public Optional<Usuario> login(String correo, String contraseña) {
        return usuarioRepository.findByCorreo(correo)
                .filter(u -> passwordEncoder.matches(contraseña, u.getContraseña()));
    }
    public List<Usuario> listarUsuarios() { return usuarioRepository.findAll(); }
    public Optional<Usuario> buscarPorId(Long id) { return usuarioRepository.findById(id); }
    public List<Usuario> buscarPorRol(Rol rol) { return usuarioRepository.findByRol(rol); }
    public Usuario cambiarEstado(Long id, boolean activo) {
        return usuarioRepository.findById(id).map(u -> {
            u.setActivo(activo);
            return usuarioRepository.save(u);
        }).orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no encontrado."));
    }
    public Usuario actualizarUsuario(Long id, Usuario d) {
        return usuarioRepository.findById(id).map(u -> {
            u.setNombre(d.getNombre());
            if (d.getContraseña() != null && !d.getContraseña().isEmpty())
                u.setContraseña(passwordEncoder.encode(d.getContraseña()));
            if (d.getRol() != null) u.setRol(d.getRol());
            u.setActivo(d.isActivo());
            return usuarioRepository.save(u);
        }).orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no encontrado."));
    }
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id))
            throw new RuntimeException("Usuario con ID " + id + " no existe.");
        usuarioRepository.deleteById(id);
    }
}
