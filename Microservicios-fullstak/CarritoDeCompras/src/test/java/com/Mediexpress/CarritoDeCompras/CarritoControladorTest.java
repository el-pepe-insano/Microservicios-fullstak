package com.Mediexpress.CarritoDeCompras;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.Mediexpress.CarritoDeCompras.controller.CarritoControlador;
import com.Mediexpress.CarritoDeCompras.model.CarritoItem;
import com.Mediexpress.CarritoDeCompras.service.CarritoServicio;

@WebMvcTest(CarritoControlador.class)
public class CarritoControladorTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarritoServicio servicio;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAgregarItem() throws Exception {
        CarritoItem item = new CarritoItem(1L, 1L, 1L, 3);
        Mockito.when(servicio.agregar(any(CarritoItem.class))).thenReturn(item);

        mockMvc.perform(post("/api/v1/carrito")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cantidad").value(3));
    }

    @Test
    void testObtenerPorCliente() throws Exception {
        CarritoItem item = new CarritoItem(1L, 2L, 1L, 5);
        Mockito.when(servicio.obtenerPorCliente(2L)).thenReturn(Collections.singletonList(item));

        mockMvc.perform(get("/api/v1/carrito/cliente/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testObtenerPorId() throws Exception {
        CarritoItem item = new CarritoItem(1L, 1L, 1L, 3);
        Mockito.when(servicio.obtenerPorId(1L)).thenReturn(Optional.of(item));

        mockMvc.perform(get("/api/v1/carrito/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testActualizarItem() throws Exception {
        CarritoItem item = new CarritoItem(1L, 1L, 1L, 2);
        Mockito.when(servicio.obtenerPorId(1L)).thenReturn(Optional.of(item));
        Mockito.when(servicio.actualizar(any(CarritoItem.class))).thenReturn(item);

        mockMvc.perform(put("/api/v1/carrito/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidad").value(2));
    }

    @Test
    void testEliminarItem() throws Exception {
        mockMvc.perform(delete("/api/v1/carrito/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testVaciarCarrito() throws Exception {
        mockMvc.perform(delete("/api/v1/carrito/cliente/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testTotalCantidad() throws Exception {
        Mockito.when(servicio.obtenerTotalCantidad(1L)).thenReturn(10);

        mockMvc.perform(get("/api/v1/carrito/cliente/1/total"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }
} 


