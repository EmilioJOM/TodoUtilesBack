package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Product;
import java.util.List;

<<<<<<< HEAD
public interface SearchService {
    List<Product> getProductsByPrice(double price);
    List<Product> getProductsByDescription(String q);
    List<Product> getProductsByCategory(String category);
=======
//import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.exceptions.NoSearchResultsException;

public interface SearchService {

    public List<Product> getProductsByPrice(double price) throws NoSearchResultsException;

    public List<Product> getProductsByDescription(String description) throws NoSearchResultsException;

    //La idea seria que le pase la descripcion de la categoria pero esta sujeto a cambios supongo
    public List<Product> getProductsByCategory(String category) throws NoSearchResultsException;

    public List<Product> getProductsByCategoryPrice(String category,double price) throws NoSearchResultsException;
    
>>>>>>> origin/main
}
