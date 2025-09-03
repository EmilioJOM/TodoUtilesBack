package com.uade.tpo.demo.service;

import java.util.List;

//import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Product;

public interface SearchService {

    public List<Product> getProductsByPrice(double price);

    public List<Product> getProductsByDescription(String description);

    //La idea seria que le pase la descripcion de la categoria pero esta sujeto a cambios supongo
    public List<Product> getProductsByCategory(String category);
    
}
