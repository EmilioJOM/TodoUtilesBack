package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.exceptions.CategoryNonexistentException;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    
    public Product createProduct(String description, int stock, double price, String extraInfo);

    public Boolean eraseProduct(Long id);

    public Product addProductCategory(long id, String categoryDescription) throws CategoryNonexistentException;

    public Product deleteProductCategory(long id, String categoryDescription) throws CategoryNonexistentException;

    public Product changeDescription(long id, String description);

    public Product changeExtraInfo(long id, String info);

    public Product addStock(long id, int stock);

    public List<Product> getAllProducts();

    public Product obtainProduct(Long id);

    public Product changePrice(long id, double price);

    Product subirImagen(Long id, MultipartFile file);

    void asociarImagen(Long productId, Long imagenId);

}
