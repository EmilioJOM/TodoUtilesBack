package com.uade.tpo.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "No se han podido encontrar productos que coincidan con tu busqueda")
public class NoSearchResultsException extends Exception {
    
}
