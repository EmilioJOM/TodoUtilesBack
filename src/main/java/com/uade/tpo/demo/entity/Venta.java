package com.uade.tpo.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;

    private Long idUsuario;

    private LocalDateTime fecha;

    private Double total;

    private String metodoPago;

    private Long idCupon;

    private String estado; // PENDING | COMPRADA | CANCELADA

    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
