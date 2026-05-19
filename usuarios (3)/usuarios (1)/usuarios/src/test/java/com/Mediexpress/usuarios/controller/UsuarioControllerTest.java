package com.Mediexpress.usuarios.controller;

import com.Mediexpress.usuarios.model.Rol;
import com.Mediexpress.usuarios.model.Usuario;
import com.Mediexpress.usuarios.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Usuario getUsuarioEjemplo() {
        Rol rol = Rol.builder().idRol(1L).nombre("ADMIN").build();

        return Usuario.builder()
                .idUsuario(1L)
                .nombreUsuario("Juan")
                .contraseña("123456")
                .correo("juan@mail.com")
                .disponible(true)
                .rol(rol)
                .build();
    }

    @Test
    void testListarUsuarios() throws Exception {
        Usuario usuario = getUsuarioEjemplo();
        List<Usuario> usuarios = Arrays.asList(usuario);

        when(usuarioService.listarUsuarios()).thenReturn(usuarios);

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreUsuario").value("Juan"));
    }

    @Test
    void testBuscarPorId_existente() throws Exception {
        Usuario usuario = getUsuarioEjemplo();

        when(usuarioService.buscarUsuarioPorId(1L)).thenReturn(usuario);

        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correo").value("juan@mail.com"));
    }

    @Test
    void testBuscarPorId_noExiste() throws Exception {
        when(usuarioService.buscarUsuarioPorId(99L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/usuarios/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGuardarUsuario() throws Exception {
        Usuario usuario = getUsuarioEjemplo();

        when(usuarioService.agregarUsuario(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreUsuario").value("Juan"));
    }

    @Test
    void testActualizarUsuario_existente() throws Exception {
        Usuario usuario = getUsuarioEjemplo();

        when(usuarioService.buscarUsuarioPorId(1L)).thenReturn(usuario);
        when(usuarioService.actualizarUsuario(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(put("/api/v1/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correo").value("juan@mail.com"));
    }

    @Test
    void testEliminarUsuario_existente() throws Exception {
        Usuario usuario = getUsuarioEjemplo();

        when(usuarioService.buscarUsuarioPorId(1L)).thenReturn(usuario);
        Mockito.doNothing().when(usuarioService).eliminarUsuario(1L);

        mockMvc.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarUsuario_noExiste() throws Exception {
        when(usuarioService.buscarUsuarioPorId(99L)).thenReturn(null);

        mockMvc.perform(delete("/api/v1/usuarios/99"))
                .andExpect(status().isNotFound());
    }
}