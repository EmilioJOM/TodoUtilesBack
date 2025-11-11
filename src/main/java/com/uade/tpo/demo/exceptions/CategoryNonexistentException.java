package com.uade.tpo.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Categoría no encontrada")
public class CategoryNonexistentException extends RuntimeException {
    
    public CategoryNonexistentException() {
        super("Categoría no encontrada");
    }
    
    public CategoryNonexistentException(String message) {
        super(message);
    }
    
    public CategoryNonexistentException(String message, Throwable cause) {
        super(message, cause);
    }
}