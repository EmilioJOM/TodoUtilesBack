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

    /* 
    @Override
    public List<Product> getProductsByPrice(double price) {
        return productRepository.findByPriceLessThanEqual(price);
    }
    */

    /* 
    @Override
    public List<Product> getProductsByDescription(String q) {
        if (q == null || q.isBlank()) return Collections.emptyList();
        return productRepository.findByDescriptionContainingIgnoreCase(q);
    }
    */

    /* 
    @Override
    public List<Product> getProductsByCategory(String category) {
        if (category == null || category.isBlank()) return Collections.emptyList();
        try {
            Long id = Long.valueOf(category);
            return productRepository.findByCategories_Id(id);
        } catch (NumberFormatException e) {
            return Collections.emptyList();
        }
    }
    */


    //Busca filtrando por precio
    @Transactional(rollbackFor = Throwable.class)
    public List<Product> getProductsByPrice(double price) throws NoSearchResultsException{
        List<Product> aux= productRepository.findByPrice(price);
        if (aux.isEmpty()){
            throw new NoSearchResultsException();
        }
        return aux;
    }

    //busca filtrando por descripcion - nombre
    @Transactional(rollbackFor = Throwable.class)
    public List<Product> getProductsByDescription(String description) throws NoSearchResultsException{
        List<Product> aux=productRepository.findByDescription(description);
        if(aux.isEmpty()){
            throw new NoSearchResultsException();
        }
        return aux;
    }

    // busca filtrando por categoria
    @Transactional(rollbackFor = Throwable.class)
    public List<Product> getProductsByCategory(Long category) throws NoSearchResultsException{
        List<Product> aux=productRepository.findByCategories_Id(category);
        if(aux.isEmpty()){
            throw new NoSearchResultsException();
        }
        return aux;
    }

    // busca filtrando por categoria y precio
    @Transactional(rollbackFor = Throwable.class)
    public List<Product> getProductsByCategoryPrice(String category,double price) throws NoSearchResultsException{
        List<Product> aux= productRepository.findByCategoryDescriptionAndPrice(category,price);
        if (aux.isEmpty()){
            throw new NoSearchResultsException();
        }
        return aux;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public List<Product> getProductsByCategory(String category) throws NoSearchResultsException {
        if (category == null || category.isBlank()) {
        throw new NoSearchResultsException();
        }
        List<Product> aux = productRepository.findByCategoryDescription(category);
        if (aux.isEmpty()) {
            throw new NoSearchResultsException();
        }
        return aux;
    }


    
}

