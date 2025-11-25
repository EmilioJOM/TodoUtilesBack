package com.uade.tpo.demo.controllers.products;

import java.awt.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
    
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private String descripcion;
    private int stock;
    private double price;
    private String extraInfo;
    private String category;
    
}
