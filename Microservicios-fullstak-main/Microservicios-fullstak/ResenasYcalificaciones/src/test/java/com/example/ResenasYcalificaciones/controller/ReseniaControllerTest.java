package com.example.ResenasYcalificaciones.controller;

import com.example.ResenasYcalificaciones.Service.ReseniaServicio;
import com.example.ResenasYcalificaciones.Service.UsuarioClienteService;
import com.example.ResenasYcalificaciones.model.Resenia;
import com.example.ResenasYcalificaciones.model.Usuario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import reactor.core.publisher.Mono;
@WebMvcTest(ReseniaController.class)
class ReseniaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReseniaServicio servicio;

    @MockBean
    private UsuarioClienteService usuarioClienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    private Usuario crearUsuarioEjemplo() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setNombreUsuario("Juan Perez");
        usuario.setCorreo("juan@mail.com");
        usuario.setRol("ADMIN");
        return usuario;
    }

    @Test
    void crearResenia_valida_retornaCreated() throws Exception {
        Resenia resenia = new Resenia(null, 1L, 1L, 5, "Excelente producto", LocalDateTime.now());

        when(servicio.guardar(any(Resenia.class))).thenReturn(resenia);

        mockMvc.perform(post("/resenias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resenia)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.comentario").value("Excelente producto"));
    }

    @Test
    void crearResenia_invalida_retornaBadRequest() throws Exception {
        when(servicio.guardar(any(Resenia.class))).thenThrow(new RuntimeException("Datos inválidos"));

        Resenia resenia = new Resenia();

        mockMvc.perform(post("/resenias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resenia)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Datos inválidos"));
    }

    @Test
    void listarTodas_conResultados_retornaOk() throws Exception {
        List<Resenia> lista = List.of(new Resenia(1L, 1L, 1L, 4, "Muy bueno", LocalDateTime.now()));
        when(servicio.buscarTodas()).thenReturn(lista);

        mockMvc.perform(get("/resenias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].calificacion").value(4));
    }

    @Test
    void listarTodas_sinResultados_retornaNoContent() throws Exception {
        when(servicio.buscarTodas()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/resenias"))
                .andExpect(status().isNoContent());
    }

    @Test
    void obtenerPorProducto_conResultados_retornaOk() throws Exception {
        List<Resenia> lista = List.of(new Resenia(1L, 2L, 1L, 3, "Regular", LocalDateTime.now()));
        when(servicio.buscarPorProducto(2L)).thenReturn(lista);

        mockMvc.perform(get("/resenias/producto/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comentario").value("Regular"));
    }

    @Test
    void obtenerPorProducto_sinResultados_retornaNoContent() throws Exception {
        when(servicio.buscarPorProducto(99L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/resenias/producto/99"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminarResenia_existente_retornaNoContent() throws Exception {
        doNothing().when(servicio).eliminar(1L);

        mockMvc.perform(delete("/resenias/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminarResenia_noExistente_retornaBadRequest() throws Exception {
        Mockito.doThrow(new RuntimeException("No encontrada")).when(servicio).eliminar(999L);

        mockMvc.perform(delete("/resenias/999"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No encontrada"));
    }

    @Test
    void obtenerUsuarioPorId_retornaUsuario() throws Exception {
        Usuario usuario = crearUsuarioEjemplo();

        when(usuarioClienteService.obtenerUsuarioPorId(1L)).thenReturn(Mono.just(usuario));

        mockMvc.perform(get("/resenias/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreUsuario").value("Juan Perez"))
                .andExpect(jsonPath("$.correo").value("juan@mail.com"))
                .andExpect(jsonPath("$.rol").value("ADMIN"));
    }
}