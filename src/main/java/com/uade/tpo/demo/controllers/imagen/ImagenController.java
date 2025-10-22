package com.uade.tpo.demo.controllers.imagen;

import com.uade.tpo.demo.controllers.imagen.dto.ImageResponse;
import com.uade.tpo.demo.entity.Imagen;
import com.uade.tpo.demo.service.ImageService;
import com.uade.tpo.demo.service.ImageStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@RestController @RequestMapping("/images") @RequiredArgsConstructor
public class ImagenController {
    private final ImageStorageService imageStorageService;
    private final ImageService imageService;

        @GetMapping
        public ResponseEntity<ImageResponse> get(@RequestParam Long id) throws Exception {
            Imagen img = imageService.viewById(id);
            String b64 = java.util.Base64.getEncoder()
                    .encodeToString(img.getImage().getBytes(1, (int) img.getImage().length()));
            return ResponseEntity.ok(ImageResponse.builder().id(id).file(b64).build());
        }

        @PostMapping
        public ResponseEntity<String> upload(@RequestPart("file") MultipartFile file) throws Exception {
            Imagen saved = imageService.create(file);
            return ResponseEntity.ok("created:" + saved.getId());
        }
    }

