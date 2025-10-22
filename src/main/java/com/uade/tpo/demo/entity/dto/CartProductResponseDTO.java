package com.uade.tpo.demo.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartProductResponseDTO {
    private Long productId;     
    private String description;
    private double price;
    private int quantity;
    private int stock;
}
