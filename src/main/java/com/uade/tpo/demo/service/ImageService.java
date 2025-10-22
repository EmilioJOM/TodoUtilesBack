package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Imagen;
import com.uade.tpo.demo.repository.ImagenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.awt.*;
import java.sql.Blob;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImagenRepository repo;

    public Imagen create(MultipartFile file) throws Exception {
        Blob blob = new javax.sql.rowset.serial.SerialBlob(file.getBytes());
        return repo.save(Imagen.builder().image(blob).build());
    }

    public Imagen viewById(long id) {
        return repo.findById(id).orElseThrow();
    }
}

