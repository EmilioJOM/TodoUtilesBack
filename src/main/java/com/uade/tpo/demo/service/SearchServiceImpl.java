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


    
}
