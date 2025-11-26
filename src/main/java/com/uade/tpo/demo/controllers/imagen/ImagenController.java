package com.uade.tpo.demo.controllers.imagen;

import com.uade.tpo.demo.entity.Imagen;
import com.uade.tpo.demo.service.ImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/productos") // <= IMPORTANTE: con /api
public class ImagenController {

    private final ImagenService imagenService;

    @Autowired
    public ImagenController(ImagenService imagenService) {
        this.imagenService = imagenService;
    }

    // POST /api/productos/{id}/imagen -> subir imagen
    @PostMapping("/{id}/imagen")
    public ResponseEntity<String> subirImagen(
            @PathVariable("id") Long productoId,
            @RequestParam("file") MultipartFile archivo) {
        try {
            imagenService.guardarImagen(productoId, archivo);
            return ResponseEntity.ok("Imagen guardada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar la imagen: " + e.getMessage());
        }
    }

    // GET /api/productos/{id}/imagen -> obtener imagen
    @GetMapping("/{id}/imagen")
    public ResponseEntity<byte[]> verImagen(@PathVariable("id") Long productoId) {
        Imagen imagen = imagenService.obtenerPorProducto(productoId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + imagen.getNombre() + "\"")
                .contentType(MediaType.parseMediaType(imagen.getContentType()))
                .body(imagen.getDatos());
    }
}
