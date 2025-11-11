package com.uade.tpo.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.exceptions.NoSearchResultsException;
import com.uade.tpo.demo.repository.ProductRepository;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ProductRepository productRepository;

    //Busca filtrando por precio
    @Transactional(rollbackFor = Throwable.class)
    public List<Product> getProductsByPrice(double price) {
        List<Product> aux = productRepository.findByPrice(price);
        if (aux.isEmpty()){
            throw new NoSearchResultsException("No se encontraron productos con precio: " + price);
        }
        return aux;
    }

    //busca filtrando por descripcion - nombre
    @Transactional(rollbackFor = Throwable.class)
    public List<Product> getProductsByDescription(String description) {
        List<Product> aux = productRepository.findByDescription(description);
        if(aux.isEmpty()){
            throw new NoSearchResultsException("No se encontraron productos con descripción: " + description);
        }
        return aux;
    }

    // busca filtrando por categoria
    @Transactional(rollbackFor = Throwable.class)
    public List<Product> getProductsByCategory(String category) {
        if (category == null || category.isBlank()) {
            throw new NoSearchResultsException("La categoría de búsqueda no puede estar vacía");
        }
        List<Product> aux = productRepository.findByCategoryDescription(category);
        if (aux.isEmpty()) {
            throw new NoSearchResultsException("No se encontraron productos en la categoría: " + category);
        }
        return aux;
    }

    // busca filtrando por categoria y precio
    @Transactional(rollbackFor = Throwable.class)
    public List<Product> getProductsByCategoryPrice(String category, double price) {
        List<Product> aux = productRepository.findByCategoryDescriptionAndPrice(category, price);
        if (aux.isEmpty()){
            throw new NoSearchResultsException("No se encontraron productos en categoría '" + category + "' con precio " + price);
        }
        return aux;
    }
}