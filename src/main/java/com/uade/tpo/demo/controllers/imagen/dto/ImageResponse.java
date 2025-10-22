package com.uade.tpo.demo.controllers.imagen.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ImageResponse {
    private Long id;
    private String file; // Base64
}