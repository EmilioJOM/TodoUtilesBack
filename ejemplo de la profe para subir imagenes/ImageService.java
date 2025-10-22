package com.project.service;

import com.project.model.Image;
import org.springframework.stereotype.Service;

@Service
public interface ImageService {
    public Image create(Image image);

    public Image viewById(long id);
}
