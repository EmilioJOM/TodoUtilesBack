package com.uade.tpo.demo.service;

<<<<<<< HEAD
=======
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

>>>>>>> origin/main
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.exceptions.NoSearchResultsException;
import com.uade.tpo.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class SearchServiceImpl implements SearchService {

<<<<<<< HEAD
    private final ProductRepository productRepository;

    public SearchServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProductsByPrice(double price) {
        return productRepository.findByPriceLessThanEqual(price);
    }

    @Override
    public List<Product> getProductsByDescription(String q) {
        if (q == null || q.isBlank()) return Collections.emptyList();
        return productRepository.findByDescriptionContainingIgnoreCase(q);
    }

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
=======
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
    public List<Product> getProductsByCategory(String category) throws NoSearchResultsException{
        List<Product> aux=productRepository.findByCategory(category);
        if(aux.isEmpty()){
            throw new NoSearchResultsException();
        }
        return aux;
    }

    // busca filtrando por categoria y precio
    @Transactional(rollbackFor = Throwable.class)
    public List<Product> getProductsByCategoryPrice(String category,double price) throws NoSearchResultsException{
        List<Product> aux= productRepository.findByCategoryPrice(category,price);
        if (aux.isEmpty()){
            throw new NoSearchResultsException();
        }
        return aux;
    }


    
>>>>>>> origin/main
}
