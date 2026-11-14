package com.polleria.ElGustazo.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoResponseDTO {
    private Long id;
    private LocalDateTime fecha;
    private String estado;
    private Double total;
    private String username;
    private List<DetallePedidoResponseDTO> detalles;

    public PedidoResponseDTO() {}

    public PedidoResponseDTO(Long id, LocalDateTime fecha, String estado, Double total, String username, List<DetallePedidoResponseDTO> detalles) {
        this.id = id;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
        this.username = username;
        this.detalles = detalles;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public List<DetallePedidoResponseDTO> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedidoResponseDTO> detalles) { this.detalles = detalles; }
}
