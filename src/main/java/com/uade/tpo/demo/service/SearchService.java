package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Product;
import java.util.List;

import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.exceptions.NoSearchResultsException;

public interface SearchService {

    public List<Product> getProductsByPrice(double price) throws NoSearchResultsException;

    public List<Product> getProductsByDescription(String description) throws NoSearchResultsException;

    //La idea seria que le pase la descripcion de la categoria pero esta sujeto a cambios supongo
    public List<Product> getProductsByCategory(String category) throws NoSearchResultsException;

    public List<Product> getProductsByCategoryPrice(String category,double price) throws NoSearchResultsException;
    
}
