package com.uade.tpo.demo.controllers.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticConfig implements WebMvcConfigurer {

    // Dónde LEER para servir (URI file:)
    @Value("${uploads.resource.location:file:///C:/tmp/uploads/}") // Windows dev (ver abajo perfiles)
    private String resourceLocation;

    @Value("${uploads.url.prefix:/uploads/}")
    private String urlPrefix;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(urlPrefix + "**")
                .addResourceLocations(resourceLocation);
    }
}
