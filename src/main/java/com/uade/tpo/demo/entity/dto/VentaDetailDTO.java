package com.uade.tpo.demo.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class VentaDetailDTO {

    private Long idVenta;
    private LocalDateTime fecha;
    private Double total;
    private String metodoPago;
    private String estado;
    private List<CartProductResponseDTO> productos;

}