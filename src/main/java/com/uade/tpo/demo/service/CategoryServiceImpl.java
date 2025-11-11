package com.uade.tpo.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.exceptions.CategoryDuplicateException;
import com.uade.tpo.demo.exceptions.CategoryNonexistentException;
import com.uade.tpo.demo.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Category> getCategories(PageRequest pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Optional<Category> getCategoryById(Long categoryId) {
        // Ya está bien así, devuelve Optional para que el controller decida
        return categoryRepository.findById(categoryId);
    }

    // Si quieres un método que lance excepción directamente:
    public Category getCategoryByIdOrThrow(Long categoryId) {
        return categoryRepository.findById(categoryId)
            .orElseThrow(() -> new CategoryNonexistentException("Categoría con ID " + categoryId + " no encontrada"));
    }

    @Transactional(rollbackFor = Throwable.class)
    public Category createCategory(String description) {
        // Validación de entrada
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción de la categoría no puede estar vacía");
        }
        
        String trimmedDescription = description.trim();
        List<Category> categories = categoryRepository.findByDescription(trimmedDescription);
        
        if (!categories.isEmpty()) {
            throw new CategoryDuplicateException("La categoría '" + trimmedDescription + "' ya existe");
        }
        
        Category nuevaCategoria = new Category(trimmedDescription);
        return categoryRepository.save(nuevaCategoria);
    }

    @Override
    @Transactional
    public Boolean deleteCategoryById(Long categoryId) {
        // Verificar si la categoría existe antes de eliminar
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNonexistentException("No se puede eliminar - Categoría con ID " + categoryId + " no existe");
        }
        
        categoryRepository.deleteById(categoryId);
        
        // Verificar que se eliminó correctamente
        return !categoryRepository.existsById(categoryId);
    }

    // Método adicional para actualizar categoría
    @Transactional
    public Category updateCategory(Long categoryId, String newDescription) {
        if (newDescription == null || newDescription.trim().isEmpty()) {
            throw new IllegalArgumentException("La nueva descripción no puede estar vacía");
        }
        
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new CategoryNonexistentException("Categoría con ID " + categoryId + " no encontrada para actualizar"));
        
        String trimmedDescription = newDescription.trim();
        
        // Verificar que no exista otra categoría con el mismo nombre
        List<Category> existingCategories = categoryRepository.findByDescription(trimmedDescription);
        if (!existingCategories.isEmpty() && !existingCategories.get(0).getId().equals(categoryId)) {
            throw new CategoryDuplicateException("Ya existe otra categoría con el nombre: " + trimmedDescription);
        }
        
        category.setDescription(trimmedDescription);
        return categoryRepository.save(category);
    }
}