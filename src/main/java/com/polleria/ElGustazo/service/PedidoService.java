package com.polleria.ElGustazo.service;

import com.polleria.ElGustazo.dto.*;
import com.polleria.ElGustazo.model.*;
import com.polleria.ElGustazo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PlatoRepository platoRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         UsuarioRepository usuarioRepository,
                         PlatoRepository platoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.platoRepository = platoRepository;
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> obtenerTodosLosPedidos() {
        return pedidoRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> buscarPorEstado(String estado) {
        return pedidoRepository.buscarPedidosPorEstado(estado).stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<PedidoResponseDTO> obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id).map(this::convertirAResponseDTO);
    }

    @Transactional
    public PedidoResponseDTO guardarPedido(PedidoRequestDTO requestDTO) {
        Usuario usuario = usuarioRepository.findById(requestDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + requestDTO.getUsuarioId()));

        Pedido pedido = new Pedido();
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado("PENDIENTE");
        pedido.setUsuario(usuario);

        double totalPedido = 0.0;

        for (DetallePedidoRequestDTO itemDTO : requestDTO.getDetalles()) {
            Plato plato = platoRepository.findById(itemDTO.getPlatoId())
                    .orElseThrow(() -> new RuntimeException("Plato no encontrado con ID: " + itemDTO.getPlatoId()));

            if (!plato.getDisponible()) {
                throw new RuntimeException("El plato no está disponible: " + plato.getNombre());
            }

            DetallePedido detalle = new DetallePedido();
            detalle.setPlato(plato);
            detalle.setCantidad(itemDTO.getCantidad());
            detalle.setPrecioUnitario(plato.getPrecio());
            detalle.setSubtotal(plato.getPrecio() * itemDTO.getCantidad());

            pedido.agregarDetalle(detalle);
            totalPedido += detalle.getSubtotal();
        }

        pedido.setTotal(totalPedido);
        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        return convertirAResponseDTO(pedidoGuardado);
    }

    @Transactional
    public Optional<PedidoResponseDTO> actualizarEstado(Long id, String estado) {
        return pedidoRepository.findById(id).map(pedido -> {
            pedido.setEstado(estado);
            return convertirAResponseDTO(pedidoRepository.save(pedido));
        });
    }

    @Transactional
    public boolean eliminarPedido(Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private PedidoResponseDTO convertirAResponseDTO(Pedido pedido) {
        List<DetallePedidoResponseDTO> detallesDTO = pedido.getDetalles().stream()
                .map(d -> new DetallePedidoResponseDTO(
                        d.getId(),
                        d.getPlato().getId(),
                        d.getPlato().getNombre(),
                        d.getCantidad(),
                        d.getPrecioUnitario(),
                        d.getSubtotal()
                ))
                .collect(Collectors.toList());

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getFecha(),
                pedido.getEstado(),
                pedido.getTotal(),
                pedido.getUsuario().getUsername(),
                detallesDTO
        );
    }
}
