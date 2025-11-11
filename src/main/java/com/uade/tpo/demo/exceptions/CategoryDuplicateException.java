package com.uade.tpo.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Categoría duplicada")
public class CategoryDuplicateException extends RuntimeException {
    
    public CategoryDuplicateException() {
        super("Categoría duplicada");
    }
    
    public CategoryDuplicateException(String message) {
        super(message);
    }
}