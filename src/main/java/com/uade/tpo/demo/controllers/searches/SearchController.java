package com.uade.tpo.demo.controllers.searches;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.exceptions.NoSearchResultsException;
import com.uade.tpo.demo.service.SearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/searches")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

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

    @GetMapping("categoria/{productCategory}")
    public List<Product> getProductsByCategory(@PathVariable("productCategory") String category)
            throws NoSearchResultsException {
        System.out.println("GET: searches/categoria/"+category.toString());
        return searchService.getProductsByCategory(category);
    }
}

