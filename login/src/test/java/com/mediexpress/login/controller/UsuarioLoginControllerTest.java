package com.mediexpress.login.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediexpress.login.model.UsuarioLogin;
import com.mediexpress.login.service.UsuarioLoginService;
import com.mediexpress.login.util.JwtUtil;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioLoginController.class)
@Import(com.mediexpress.login.config.SecurityConfig.class)
public class UsuarioLoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioLoginService service;

    @MockBean
    private JwtUtil jwtUtil;

    private final ObjectMapper mapper = new ObjectMapper();

    private UsuarioLogin usuario() {
        return UsuarioLogin.builder()
                .id(1L)
                .nombre("Diego")
                .correo("diego@mail.com")
                .contraseña("hash")
                .rol("CLIENTE")
                .activo(true)
                .build();
    }

    @Test
    void registrarUsuario() throws Exception {
        Mockito.when(service.registrar(Mockito.any())).thenReturn(usuario());

        mockMvc.perform(post("/api/login/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(usuario())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correo").value("diego@mail.com"));
    }

    @Test
    void loginCorrecto() throws Exception {
        Mockito.when(service.login("diego@mail.com", "1234")).thenReturn(Optional.of(usuario()));
        Mockito.when(jwtUtil.generarToken(Mockito.anyString(), Mockito.anyString())).thenReturn("token123");

        mockMvc.perform(post("/api/login/iniciar")
                        .param("correo", "diego@mail.com")
                        .param("contraseña", "1234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token123"));
    }

    @Test
    void loginIncorrecto() throws Exception {
        Mockito.when(service.login("x@mail.com", "fail")).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/login/iniciar")
                        .param("correo", "x@mail.com")
                        .param("contraseña", "fail"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void listarUsuarios() throws Exception {
        Mockito.when(service.listar()).thenReturn(Arrays.asList(usuario()));

        mockMvc.perform(get("/api/login"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void actualizarUsuario() throws Exception {
        Mockito.when(service.actualizar(Mockito.eq(1L), Mockito.any())).thenReturn(usuario());

        mockMvc.perform(put("/api/login/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(usuario())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Diego"));
    }

    @Test
    void eliminarUsuario() throws Exception {
        Mockito.doNothing().when(service).eliminar(1L);

        mockMvc.perform(delete("/api/login/1"))
                .andExpect(status().isNoContent());
    }
}