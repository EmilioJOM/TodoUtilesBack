package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Imagen;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImagenService {

    void guardarImagen(Long productoId, MultipartFile archivo) throws IOException;

    Imagen obtenerPorProducto(Long productoId);
}
