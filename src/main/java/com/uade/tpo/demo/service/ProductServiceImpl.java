package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.exceptions.CategoryNonexistentException;
import com.uade.tpo.demo.repository.CategoryRepository;
import com.uade.tpo.demo.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public Product obtainProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }


    public Product createProduct(String description, int stock, double price){
        Product auxProd=new Product(description,stock,price);
        validarBasico(auxProd);
        productRepository.save(auxProd);
        return auxProd;
    }


    public Product changePrice(long id, double price){
        Product auxProd= obtainProduct(id);
        auxProd.setPrice(price);
        return productRepository.save(auxProd);
    }


    @Transactional(rollbackFor = Throwable.class)
    public Product addProductCategory(long id, String categoryDescription) throws CategoryNonexistentException{
        List<Category> category=categoryRepository.findCategoryByDescription(categoryDescription);
        if(category.isEmpty()){
            throw new CategoryNonexistentException();
        }
        Product auxProd=obtainProduct(id);
        auxProd.addCategory(category.get(0));
        return productRepository.save(auxProd);
    }


    @Transactional(rollbackFor = Throwable.class)
    public Product deleteProductCategory(long id, String categoryDescription) throws CategoryNonexistentException{
        List<Category> category=categoryRepository.findCategoryByDescription(categoryDescription);
        if(category.isEmpty()){
            throw new CategoryNonexistentException();
        }
        Product auxProd=obtainProduct(id);
        auxProd.deleteCategory(category.get(0));
        return productRepository.save(auxProd);
        
    }


    public Product changeDescription(long id, String description){
        Product auxProd=obtainProduct(id);
        auxProd.setDescription(description);
        return productRepository.save(auxProd);
    }

    public Product addStock(long id, int stock){
        Product auxProd=obtainProduct(id);
        auxProd.setStock(auxProd.getStock()+stock);
        return productRepository.save(auxProd);
    }


    public Boolean eraseProduct(Long id) {
        productRepository.deleteById(id);
        if(productRepository.findById(id).isPresent()){
            return false;
        }
        return true;
    }


    private void validarBasico(Product p) {
        if (p.getDescription() == null || p.getDescription().isBlank()) {
            throw new RuntimeException("Descripción requerida.");
        }
        if (p.getPrice() <= 0) {
            throw new RuntimeException("Precio debe ser > 0.");
        }
        if (p.getStock() <0){
            throw new RuntimeException("No puede haber un stock negativo.");
        }
    }

}
