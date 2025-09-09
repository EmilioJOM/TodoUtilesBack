package com.uade.tpo.demo.exceptions;

import java.util.Map;

public class InsufficientStockException extends RuntimeException {
    private final Map<String, Integer> productsWithStock;

    public InsufficientStockException(Map<String, Integer> productsWithStock) {
        super("Stock insuficiente para uno o más productos");
        this.productsWithStock = productsWithStock;
    }

    public Map<String, Integer> getProductsWithStock() {
        return productsWithStock;
    }
}
