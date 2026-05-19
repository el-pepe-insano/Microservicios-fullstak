package com.mediexpress.HistorialDePedidos.controller;

import com.mediexpress.HistorialDePedidos.service.PedidoService;
import com.mediexpress.HistorialDePedidos.service.UsuarioClienteService;
import com.mediexpress.HistorialDePedidos.model.Pedido;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PedidoController.class)
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
private PedidoService pedidoService;

    @MockBean
    private UsuarioClienteService usuarioClienteService;
    private Pedido pedido;

    @BeforeEach
    void setUp() {
        pedido = new Pedido(1L, 10L, LocalDate.of(2023, 7, 5), 1500.0, "PENDIENTE");
    }

    @Test
    void listarTodos_retornaOkConLista() throws Exception {
        when(pedidoService.listarTodos()).thenReturn(List.of(pedido));

        mockMvc.perform(get("/api/v1/pedidos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(pedido.getId()))
                .andExpect(jsonPath("$[0].idCliente").value(pedido.getIdCliente()))
                .andExpect(jsonPath("$[0].total").value(pedido.getTotal()))
                .andExpect(jsonPath("$[0].estado").value(pedido.getEstado()));

        verify(pedidoService).listarTodos();
    }

    @Test
    void listarTodos_retornaNoContentCuandoListaVacia() throws Exception {
        when(pedidoService.listarTodos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/pedidos"))
                .andExpect(status().isNoContent());

        verify(pedidoService).listarTodos();
    }

    @Test
    void obtenerPorId_retornaOkConPedido() throws Exception {
        when(pedidoService.obtenerPorId(1L)).thenReturn(Optional.of(pedido));

        mockMvc.perform(get("/api/v1/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(pedido.getId()))
                .andExpect(jsonPath("$.idCliente").value(pedido.getIdCliente()))
                .andExpect(jsonPath("$.total").value(pedido.getTotal()))
                .andExpect(jsonPath("$.estado").value(pedido.getEstado()));

        verify(pedidoService).obtenerPorId(1L);
    }

    @Test
    void obtenerPorId_retornaNotFoundCuandoNoExiste() throws Exception {
        when(pedidoService.obtenerPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/pedidos/1"))
                .andExpect(status().isNotFound());

        verify(pedidoService).obtenerPorId(1L);
    }

    @Test
    void crearPedido_retornaCreatedConPedido() throws Exception {
        when(pedidoService.guardar(any(Pedido.class))).thenReturn(pedido);

        String json = """
                {
                    "idCliente": 10,
                    "fecha": "2023-07-05",
                    "total": 1500.0,
                    "estado": "PENDIENTE"
                }
                """;

        mockMvc.perform(post("/api/v1/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(pedido.getId()))
                .andExpect(jsonPath("$.idCliente").value(pedido.getIdCliente()))
                .andExpect(jsonPath("$.total").value(pedido.getTotal()))
                .andExpect(jsonPath("$.estado").value(pedido.getEstado()));

        verify(pedidoService).guardar(any(Pedido.class));
    }

    @Test
    void actualizarPedido_retornaOkConPedidoActualizado() throws Exception {
        when(pedidoService.actualizar(eq(1L), any(Pedido.class))).thenReturn(pedido);

        String json = """
                {
                    "idCliente": 10,
                    "fecha": "2023-07-05",
                    "total": 1500.0,
                    "estado": "PENDIENTE"
                }
                """;

        mockMvc.perform(put("/api/v1/pedidos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(pedido.getId()))
                .andExpect(jsonPath("$.idCliente").value(pedido.getIdCliente()))
                .andExpect(jsonPath("$.total").value(pedido.getTotal()))
                .andExpect(jsonPath("$.estado").value(pedido.getEstado()));

        verify(pedidoService).actualizar(eq(1L), any(Pedido.class));
    }

    @Test
    void eliminarPedido_retornaNoContent() throws Exception {
        doNothing().when(pedidoService).eliminar(1L);

        mockMvc.perform(delete("/api/v1/pedidos/1"))
                .andExpect(status().isNoContent());

        verify(pedidoService).eliminar(1L);
    }

    @Test
    void eliminarPedido_retornaBadRequestCuandoExcepcion() throws Exception {
        doThrow(new RuntimeException("El pedido no existe")).when(pedidoService).eliminar(1L);

        mockMvc.perform(delete("/api/v1/pedidos/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El pedido no existe"));

        verify(pedidoService).eliminar(1L);
    }
}