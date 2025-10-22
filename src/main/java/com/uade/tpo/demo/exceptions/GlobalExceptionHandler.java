package com.uade.tpo.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Maneja DELETE /categories/{id} cuando no existe → 404 Not Found sin body
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Void> handleNotFoundExceptions(NoSuchElementException ex) {
        System.out.println("⚠️ Recurso no encontrado: " + ex.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Manejo genérico para cualquier otra excepción no prevista
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleGenericException(Exception ex) {
        System.out.println("❌ Error no manejado: " + ex.getClass().getSimpleName() + " - " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}