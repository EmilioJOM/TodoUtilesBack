package com.uade.tpo.demo.controllers.searches;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public List<Product> getProductsByPrice(@PathVariable("productPrice") double price)
            throws NoSearchResultsException {
        System.out.println("GET: searches/precio/"+price);
        return searchService.getProductsByPrice(price);
    }

    @GetMapping("producto/{productDescription}")
    public List<Product> getProductsByDescription(@PathVariable("productDescription") String description)
            throws NoSearchResultsException {
        System.out.println("GET: searches/producto/"+description);
        return searchService.getProductsByDescription(description);
    }

    @GetMapping("category/{productCategory}")
    public List<Product> getProductsByCategory(@PathVariable("productCategory") String category)
            throws NoSearchResultsException {
        System.out.println("GET: searches/categoria/"+category.toString());
        return searchService.getProductsByCategory(category);
    }

    @GetMapping("product/{category}/{price}")
    public List<Product> getProductsByCategoryPrice(@PathVariable String category, @PathVariable double price) throws NoSearchResultsException {
        return searchService.getProductsByCategoryPrice(category, price);
}

    
}

