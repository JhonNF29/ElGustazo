package com.polleria.ElGustazo.dto;

public class DetallePedidoResponseDTO {
    private Long id;
    private Long platoId;
    private String platoNombre;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;

    public DetallePedidoResponseDTO() {}

    public DetallePedidoResponseDTO(Long id, Long platoId, String platoNombre, Integer cantidad, Double precioUnitario, Double subtotal) {
        this.id = id;
        this.platoId = platoId;
        this.platoNombre = platoNombre;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPlatoId() { return platoId; }
    public void setPlatoId(Long platoId) { this.platoId = platoId; }
    public String getPlatoNombre() { return platoNombre; }
    public void setPlatoNombre(String platoNombre) { this.platoNombre = platoNombre; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }
    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
}
