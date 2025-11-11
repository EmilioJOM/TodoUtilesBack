package com.uade.tpo.demo.exceptions;

import java.util.Map;

public class InsufficientStockException extends RuntimeException {
    private final Map<String, Integer> productsWithStock;

    // Constructor con Map (el que ya tienes)
    public InsufficientStockException(Map<String, Integer> productsWithStock) {
        super("Stock insuficiente para uno o más productos");
        this.productsWithStock = productsWithStock;
    }

    // NUEVO: Constructor con mensaje String
    public InsufficientStockException(String message) {
        super(message);
        this.productsWithStock = null;
    }

    public Map<String, Integer> getProductsWithStock() {
        return productsWithStock;
    }
}