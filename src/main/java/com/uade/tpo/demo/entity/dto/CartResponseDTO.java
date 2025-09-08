package com.uade.tpo.demo.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartResponseDTO {
    private Long id;
    private double subtotal;
    private String firstName;
    private String lastName;
    private String email;
}
