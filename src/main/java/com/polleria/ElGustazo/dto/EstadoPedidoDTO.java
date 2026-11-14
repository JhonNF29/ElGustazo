package com.polleria.ElGustazo.dto;

import jakarta.validation.constraints.NotBlank;

public class EstadoPedidoDTO {

    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
