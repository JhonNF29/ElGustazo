package com.polleria.ElGustazo.service;

import com.polleria.ElGustazo.model.Pedido;
import com.polleria.ElGustazo.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    void testListarPedidos() {
        // 1. Arrange (Preparar datos de prueba simulados)
        Pedido pedido1 = new Pedido();
        pedido1.setId(1L);
        
        Pedido pedido2 = new Pedido();
        pedido2.setId(2L);

        when(pedidoRepository.findAll()).thenReturn(Arrays.asList(pedido1, pedido2));

        // 2. Act (Ejecutar el método del servicio)
        List<Pedido> resultado = pedidoService.listarPedidos(); 

        // 3. Assert (Verificar que el resultado sea el esperado)
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(pedidoRepository, times(1)).findAll();
    }
}