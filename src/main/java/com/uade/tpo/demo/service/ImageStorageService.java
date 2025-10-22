package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Imagen;
import com.uade.tpo.demo.repository.ImagenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@Service @RequiredArgsConstructor
public class ImageStorageService {
    private final ImagenRepository repo;

    public Imagen saveAsJpg(MultipartFile file, String logicalType, String logicalName) throws Exception {
        if (file == null || file.isEmpty()) throw new IllegalArgumentException("Archivo vacío");
        String ct = file.getContentType();
        if (ct == null || !(ct.equals("image/jpeg") || ct.equals("image/png") || ct.equals("image/webp")))
            throw new IllegalArgumentException("Tipo de imagen no soportado");

        BufferedImage img = ImageIO.read(file.getInputStream());
        if (img == null) throw new IllegalArgumentException("Imagen inválida");

        //        byte[] jpg = ImageUtils.toJpgBytes(img, 0.9f); // calidad 90%
        //        Imagen asset = Imagen.builder()
        //                .filename(logicalName != null ? logicalName : file.getOriginalFilename())
        //                .extension("jpg")
        //                .bytes(jpg)
        //                .type(logicalType != null ? logicalType : "GENERIC")
        //                .build();
        //        return repo.save(asset);
        return null;
    }

    public Imagen get(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Imagen no encontrada"));
    }
}
