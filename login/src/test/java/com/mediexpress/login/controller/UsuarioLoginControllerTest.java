package com.mediexpress.login.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediexpress.login.model.UsuarioLogin;
import com.mediexpress.login.service.UsuarioLoginService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioLoginController.class)
public class UsuarioLoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioLoginService service;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void registrarUsuario() throws Exception {
        UsuarioLogin user = new UsuarioLogin(1L, "Diego", "diego@mail.com", "1234", true);
        Mockito.when(service.registrar(Mockito.any())).thenReturn(user);

        mockMvc.perform(post("/api/login/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correo").value("diego@mail.com"));
    }

    @Test
    void loginCorrecto() throws Exception {
        UsuarioLogin user = new UsuarioLogin(1L, "Diego", "diego@mail.com", "1234", true);
        Mockito.when(service.login("diego@mail.com", "1234")).thenReturn(Optional.of(user));

        mockMvc.perform(post("/api/login/iniciar")
                        .param("correo", "diego@mail.com")
                        .param("contraseña", "1234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correo").value("diego@mail.com"));
    }

    @Test
    void loginIncorrecto() throws Exception {
        Mockito.when(service.login("x@mail.com", "fail")).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/login/iniciar")
                        .param("correo", "x@mail.com")
                        .param("contraseña", "fail"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString("Credenciales incorrectas")));
    }

    @Test
    void listarUsuarios() throws Exception {
        UsuarioLogin user1 = new UsuarioLogin(1L, "Ana", "ana@mail.com", "123", true);
        UsuarioLogin user2 = new UsuarioLogin(2L, "Luis", "luis@mail.com", "abc", true);
        Mockito.when(service.listar()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/api/login"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void actualizarUsuario() throws Exception {
        UsuarioLogin actualizado = new UsuarioLogin(1L, "AnaActualizada", "ana@mail.com", "newpass", true);
        Mockito.when(service.actualizar(Mockito.eq(1L), Mockito.any())).thenReturn(actualizado);

        mockMvc.perform(put("/api/login/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("AnaActualizada"));
    }

    @Test
    void eliminarUsuario() throws Exception {
        Mockito.doNothing().when(service).eliminar(1L);

        mockMvc.perform(delete("/api/login/1"))
                .andExpect(status().isNoContent());
    }
}

