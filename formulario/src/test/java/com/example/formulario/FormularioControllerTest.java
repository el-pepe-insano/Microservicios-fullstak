package com.example.formulario;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.formulario.formulario.controller.FormularioController;
import com.example.formulario.formulario.model.Formulario;
import com.example.formulario.formulario.service.FormularioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(FormularioController.class)
public class FormularioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FormularioService formularioService;  // Mock al servicio, no al repositorio

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testObtenerFormularios_conDatos() throws Exception {
        Formulario f1 = new Formulario(1L, "Consulta1", "Problema1");
        Formulario f2 = new Formulario(2L, "Consulta2", "Problema2");

        List<Formulario> listaMock = Arrays.asList(f1, f2);

        when(formularioService.getFormularios()).thenReturn(listaMock);

        mockMvc.perform(get("/api/v1/formulario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].consultarProducto").value("Consulta1"));

        verify(formularioService, times(1)).getFormularios();
    }

    @Test
    public void testObtenerFormularios_sinDatos() throws Exception {
        when(formularioService.getFormularios()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/formulario"))
                .andExpect(status().isNoContent());

        verify(formularioService, times(1)).getFormularios();
    }

    @Test
    public void testObtenerFormularioPorId_existente() throws Exception {
        Formulario formularioMock = new Formulario(1L, "Consulta1", "Problema1");

        when(formularioService.getFormulario(1L)).thenReturn(formularioMock);

        mockMvc.perform(get("/api/v1/formulario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.consultarProducto").value("Consulta1"));
    }

    @Test
    public void testObtenerFormularioPorId_noExiste() throws Exception {
        when(formularioService.getFormulario(1L)).thenThrow(new RuntimeException("Formulario no encontrado"));

        mockMvc.perform(get("/api/v1/formulario/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCrearFormulario_valido() throws Exception {
        Formulario formularioNuevo = new Formulario(null, "ConsultaNueva", "ProblemaNuevo");
        Formulario formularioGuardado = new Formulario(1L, "ConsultaNueva", "ProblemaNuevo");

        when(formularioService.saveFormulario(anyString(), anyString())).thenReturn(formularioGuardado);

        mockMvc.perform(post("/api/v1/formulario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(formularioNuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.consultarProducto").value("ConsultaNueva"));

        verify(formularioService, times(1)).saveFormulario(anyString(), anyString());
    }

    @Test
public void testEliminarFormulario_existente() throws Exception {
    when(formularioService.deleteFormulario(1L)).thenReturn("Formulario eliminado correctamente");

    mockMvc.perform(delete("/api/v1/formulario/1"))
            .andExpect(status().isNoContent());

    verify(formularioService, times(1)).deleteFormulario(1L);
}

@Test
public void testEliminarFormulario_noExiste() throws Exception {
    when(formularioService.deleteFormulario(1L)).thenThrow(new RuntimeException("El formulario no existe"));

    mockMvc.perform(delete("/api/v1/formulario/1"))
            .andExpect(status().isNotFound());

    verify(formularioService, times(1)).deleteFormulario(1L);
}
}

