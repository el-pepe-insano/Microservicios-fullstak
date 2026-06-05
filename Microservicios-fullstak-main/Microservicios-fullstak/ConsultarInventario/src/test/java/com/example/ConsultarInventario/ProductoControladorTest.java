package com.example.ConsultarInventario;

import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.ConsultarInventario.controller.ProductoControlador;
import com.example.ConsultarInventario.model.Producto;
import com.example.ConsultarInventario.service.ProductoServicio;

@WebMvcTest(ProductoControlador.class)
public class ProductoControladorTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoServicio productoServicio;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testListarTodos_conProductos() throws Exception {
        List<Producto> productos = Arrays.asList(
                new Producto(1L, "Paracetamol", "Analgésico", 100, 1500.0),
                new Producto(2L, "Ibuprofeno", "Antiinflamatorio", 80, 2000.0));

        Mockito.when(productoServicio.listarTodos()).thenReturn(productos);

        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testListarTodos_sinProductos() throws Exception {
        Mockito.when(productoServicio.listarTodos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/productos"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testBuscarPorId_existente() throws Exception {
        Producto producto = new Producto(1L, "Paracetamol", "Analgésico", 100, 1500.0);
        Mockito.when(productoServicio.buscarPorId(1L)).thenReturn(Optional.of(producto));

        mockMvc.perform(get("/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Paracetamol"));
    }

    @Test
    public void testBuscarPorId_noExistente() throws Exception {
        Mockito.when(productoServicio.buscarPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/productos/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testBuscarPorNombre_conResultados() throws Exception {
        List<Producto> productos = List.of(new Producto(1L, "Paracetamol", "Analgésico", 100, 1500.0));
        Mockito.when(productoServicio.buscarPorNombre("para")).thenReturn(productos);

        mockMvc.perform(get("/productos/buscar").param("nombre", "para"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testBuscarPorNombre_sinResultados() throws Exception {
        Mockito.when(productoServicio.buscarPorNombre("xyz")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/productos/buscar").param("nombre", "xyz"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testCrearProducto_exitoso() throws Exception {
        Producto producto = new Producto(null, "Paracetamol", "Analgésico", 100, 1500.0);
        Producto guardado = new Producto(1L, "Paracetamol", "Analgésico", 100, 1500.0);

        Mockito.when(productoServicio.guardar(any(Producto.class))).thenReturn(guardado);

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testEliminarProducto_existente() throws Exception {
        Mockito.doNothing().when(productoServicio).eliminar(1L);

        mockMvc.perform(delete("/productos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testEliminarProducto_noExistente() throws Exception {
        Mockito.doThrow(new RuntimeException("Producto con id 1 no existe.")).when(productoServicio).eliminar(1L);

        mockMvc.perform(delete("/productos/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Producto con id 1 no existe."));
    }
}

