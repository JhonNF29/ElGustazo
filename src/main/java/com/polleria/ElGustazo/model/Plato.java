package com.polleria.ElGustazo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "platos")
@Data @NoArgsConstructor @AllArgsConstructor
public class Plato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Boolean disponible = true;
}