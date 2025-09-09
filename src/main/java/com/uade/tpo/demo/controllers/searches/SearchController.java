package com.uade.tpo.demo.controllers.searches;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.exceptions.NoSearchResultsException;
import com.uade.tpo.demo.service.SearchService;


@RestController
@RequestMapping("searches/")
public class SearchController {
    
    @Autowired
    private SearchService searchService;

    @GetMapping("precio/{productPrice}")
    public List<Product> getProductsByPrice(double price) throws NoSearchResultsException{
        return searchService.getProductsByPrice(price);
    }

    @GetMapping("producto/{productDescription}")
    public List<Product> getProductsByDescription(String description) throws NoSearchResultsException{
        return searchService.getProductsByDescription(description);
    }

    //La idea seria que le pase la descripcion de la categoria pero esta sujeto a cambios supongo
    @GetMapping("category/{productCategory}")
    public List<Product> getProductsByCategory(String category) throws NoSearchResultsException{
        return searchService.getProductsByCategory(category);
    }

    @GetMapping("product/{productCategoryPrice}")
    public List<Product> getProductsByCategoryPrice(String category, double price) throws NoSearchResultsException {
        return searchService.getProductsByCategoryPrice(category,price);
    }
    


}
