package com.elgustazo.backend.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void deberiaCrearPedidoExitosamente() throws Exception {
        String jsonBody = """
                {
                  "clienteNombre": "Jhon Nina",
                  "tipoServicio": "DELIVERY"
                }
                """;

        mockMvc.perform(post("/api/v1/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated()) // Valida el código 201
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.clienteNombre").value("Jhon Nina"))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));
    }

    @Test
    @Order(2)
    public void deberiaListarPedidos() throws Exception {
        mockMvc.perform(get("/api/v1/pedidos"))
                .andExpect(status().isOk()) // Valida el código 200
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].clienteNombre").value("Jhon Nina"));
    }
}