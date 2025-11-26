package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Imagen;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.exceptions.CategoryNonexistentException;
import com.uade.tpo.demo.repository.CategoryRepository;
import com.uade.tpo.demo.repository.ImagenRepository;
import com.uade.tpo.demo.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductServiceImpl implements ProductService{


    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private GuardarImagenes guardarImagenes;
    @Autowired
    private ImageStorageService imageStorageService;

    @Autowired ImagenRepository imagenRepository;

    public Product obtainProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }


    public Product createProduct(String description, int stock, double price,String extraInfo,String category){
        Product auxProd=new Product(description,stock,price);
        validarBasico(auxProd);
        auxProd.setExtraInfo(extraInfo);
        auxProd.addCategory(categoryRepository.findByDescription(category).get(0));
        productRepository.save(auxProd);
        return auxProd;
    }


    public Product changePrice(long id, double price){
        Product auxProd= obtainProduct(id);
        auxProd.setPrice(price);
        return productRepository.save(auxProd);
    }

    @Override
    public Product subirImagen(Long id, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo de imagen es obligatorio");
        }


        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("El archivo debe ser una imagen");
        }

        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Product p = productRepository.findById(id).orElseThrow();
        Imagen asset = null;
        try {
            asset = imageStorageService.saveAsJpg(file, "PRODUCT", "prod-" + id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        p.setImagen(asset);
        productRepository.save(p);
        return productRepository.save(producto);
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

    public Product changeExtraInfo(long id, String info){
        Product auxProd=obtainProduct(id);
        auxProd.setExtraInfo(info);
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
    @Transactional
    @Override
    public void asociarImagen(Long productId, Long imagenId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado: " + productId));

        Imagen imagen = imagenRepository.findById(imagenId)
                .orElseThrow(() -> new NoSuchElementException("Imagen no encontrada: " + imagenId));

        product.setImagen(imagen);
        productRepository.save(product);
    }

}
