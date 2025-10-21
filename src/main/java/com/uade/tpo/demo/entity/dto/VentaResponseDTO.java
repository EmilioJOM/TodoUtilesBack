package com.uade.tpo.demo.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class VentaResponseDTO {
    private Long idVenta;
    private String nombreUsuario; 
    private LocalDateTime fecha;
    private Double total;
    private String metodoPago;
    private String estado;
}