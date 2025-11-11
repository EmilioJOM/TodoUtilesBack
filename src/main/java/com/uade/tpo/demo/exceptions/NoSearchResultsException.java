package com.uade.tpo.demo.exceptions;

public class NoSearchResultsException extends RuntimeException {
    
    public NoSearchResultsException() {
        super("No se encontraron resultados");
    }
    
    public NoSearchResultsException(String message) {
        super(message);
    }
}