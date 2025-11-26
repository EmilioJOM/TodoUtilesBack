package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Imagen;
import com.uade.tpo.demo.repository.ImagenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImagenServiceImpl implements ImagenService {

    private final ImagenRepository imagenRepository;

    @Autowired
    public ImagenServiceImpl(ImagenRepository imagenRepository) {
        this.imagenRepository = imagenRepository;
    }

    @Override
    public void guardarImagen(Long productoId, MultipartFile archivo) throws IOException {
        Imagen img = new Imagen();
        img.setProductoId(productoId);
        img.setNombre(archivo.getOriginalFilename());
        img.setContentType(archivo.getContentType());
        img.setDatos(archivo.getBytes());

        imagenRepository.save(img);
    }

    @Override
    public Imagen obtenerPorProducto(Long productoId) {
        return imagenRepository.findByProductoId(productoId)
                .orElseThrow(() -> new RuntimeException("Imagen no encontrada para el producto " + productoId));
    }
}
