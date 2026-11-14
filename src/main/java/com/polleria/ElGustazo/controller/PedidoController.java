package com.polleria.ElGustazo.controller;

import com.polleria.ElGustazo.dto.EstadoPedidoDTO;
import com.polleria.ElGustazo.dto.PedidoRequestDTO;
import com.polleria.ElGustazo.dto.PedidoResponseDTO;
import com.polleria.ElGustazo.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.obtenerTodosLosPedidos());
    }

    @GetMapping("/por-estado")
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidosPorEstado(@RequestParam String estado) {
        return ResponseEntity.ok(pedidoService.buscarPorEstado(estado));
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> registrarPedido(@Valid @RequestBody PedidoRequestDTO requestDTO) {
        PedidoResponseDTO nuevoPedido = pedidoService.guardarPedido(requestDTO);
        return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> obtenerPedidoPorId(@PathVariable Long id) {
        return pedidoService.obtenerPedidoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> actualizarEstado(@PathVariable Long id,
                                                              @Valid @RequestBody EstadoPedidoDTO dto) {
        return pedidoService.actualizarEstado(id, dto.getEstado())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        if (pedidoService.eliminarPedido(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
