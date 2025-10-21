package com.uade.tpo.demo.controllers.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("Registrando handler para /uploads/**");
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/tmp/uploads/");
    }

    // Habilita CORS para todas las rutas y métodos, desde React
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //permite todas las rutas
            .allowedOriginPatterns("*") //el frontend, acepta cualquier localhost
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") //los metodos
            .allowedHeaders("*"); //cualquier encabezado

    }
}
