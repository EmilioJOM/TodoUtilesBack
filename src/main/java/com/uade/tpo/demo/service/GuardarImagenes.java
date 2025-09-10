package com.uade.tpo.demo.service;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class GuardarImagenes {

    // Ruta de filesystem donde escribir (sin "file:")
    @Value("${uploads.fs.path:/tmp/uploads}")
    private String uploadsFsPath;

    // Prefijo público bajo el que se servirán los archivos
    @Value("${uploads.url.prefix:/uploads}")
    private String uploadsUrlPrefix;

    public String guardarArchivo(MultipartFile archivo, String carpeta, String nombre) {
        if (archivo == null || archivo.isEmpty()) {
            throw new IllegalArgumentException("El archivo de imagen es obligatorio");
        }
        try {
            Path dir = java.nio.file.Paths.get(uploadsFsPath, carpeta);
            java.nio.file.Files.createDirectories(dir);

            // Evitar traversal y conservar sólo el nombre
            String original = java.nio.file.Paths.get(archivo.getOriginalFilename())
                    .getFileName().toString();
            String filename = nombre + "_" + System.currentTimeMillis() + "_" + original;

            Path dst = dir.resolve(filename);
            archivo.transferTo(dst.toFile());

            // Devolvés la URL pública (la sirve el ResourceHandler)
            return uploadsUrlPrefix + "/" + carpeta + "/" + filename;
        } catch (java.io.IOException e) {
            throw new RuntimeException("No se pudo guardar el archivo", e);
        }
    }
}

