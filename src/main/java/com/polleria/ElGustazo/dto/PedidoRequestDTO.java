package com.polleria.ElGustazo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class PedidoRequestDTO {

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

    @NotEmpty(message = "El pedido debe contener al menos un plato")
    @Valid
    private List<DetallePedidoRequestDTO> detalles;

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public List<DetallePedidoRequestDTO> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedidoRequestDTO> detalles) { this.detalles = detalles; }
}
