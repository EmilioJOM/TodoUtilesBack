package com.uade.tpo.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "El carrito está vacío, no se puede confirmar la compra")
public class EmptyCartException extends RuntimeException {  // CAMBIA: Exception → RuntimeException
    
    public EmptyCartException() {
        super("El carrito está vacío, no se puede confirmar la compra");
    }
}