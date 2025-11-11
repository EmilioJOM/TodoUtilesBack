package com.uade.tpo.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Mapa para manejar todas tus excepciones personalizadas
    @ExceptionHandler({
        CategoryDuplicateException.class,
        CategoryNonexistentException.class, 
        EmptyCartException.class,
        InsufficientStockException.class,
        NoSearchResultsException.class
    })
    public ResponseEntity<Object> handleCustomExceptions(Exception ex, WebRequest request) {
        
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false).replace("uri=", ""));
        
        // Asignar códigos HTTP específicos para cada excepción
        HttpStatus status = HttpStatus.BAD_REQUEST; // default
        
        if (ex instanceof CategoryDuplicateException) {
            status = HttpStatus.CONFLICT; // 409
        } else if (ex instanceof CategoryNonexistentException) {
            status = HttpStatus.NOT_FOUND; // 404
        } else if (ex instanceof EmptyCartException) {
            status = HttpStatus.BAD_REQUEST; // 400
        } else if (ex instanceof InsufficientStockException) {
            status = HttpStatus.UNPROCESSABLE_ENTITY; // 422
        } else if (ex instanceof NoSearchResultsException) {
            status = HttpStatus.NOT_FOUND; // 404
        }
        
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        
        return new ResponseEntity<>(body, status);
    }

    // Manejo de excepciones genéricas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false).replace("uri=", ""));
        
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false).replace("uri=", ""));
        
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}