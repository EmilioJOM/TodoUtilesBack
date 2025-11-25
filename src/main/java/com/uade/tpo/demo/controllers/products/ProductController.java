package com.uade.tpo.demo.controllers.products;

import com.uade.tpo.demo.entity.Imagen;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.exceptions.CategoryNonexistentException;
import com.uade.tpo.demo.service.ImageService;
import com.uade.tpo.demo.service.ProductService;

import java.awt.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/productos")
public class ProductController {

    @Autowired
    private ProductService productService;
    private ImageService imageService;
    // Crear producto
    @PostMapping
    public Product createProduct(@RequestBody ProductRequest request) {
        System.out.println("POST: api/productos");
        return productService.createProduct(request.getDescripcion(), request.getStock(), request.getPrice(),request.getExtraInfo());
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public Boolean eraseProduct(@PathVariable Long id) {
        System.out.println("DELETE: api/productos/"+id.toString());
        return productService.eraseProduct(id);
    }

    // Agregar categoría
    @PostMapping("/add-category")
    public Product addProductCategory(@RequestParam long id, @RequestParam String categoryDescription)
            throws CategoryNonexistentException {
        System.out.println("DELETE: api/productos/add-category");
        return productService.addProductCategory(id, categoryDescription);
    }

    // Quitar categoría
    @PostMapping("/delete-category")
    public Product deleteProductCategory(@RequestParam long id, @RequestParam String categoryDescription)
            throws CategoryNonexistentException {
        System.out.println("POST: api/productos/delete-category");
        return productService.deleteProductCategory(id, categoryDescription);
    }

    // Cambiar descripción
    @PostMapping("/change-description")
    public Product changeDescription(@RequestParam long id, @RequestParam String description) {
        System.out.println("POST: api/productos/change-description");
        return productService.changeDescription(id, description);
    }

    //cambiar info extra
    @PostMapping("/change-extraInfo")
    public Product changeExtraInfo(@RequestParam long id, @RequestParam String info){
        return productService.changeExtraInfo(id, info);
    }

    // Agregar stock
    @PostMapping("/add-stock")
    public Product addStock(@RequestParam long id, @RequestParam int stock) {
        System.out.println("POST: api/productos/add-stock");
        return productService.addStock(id, stock);
    }

    // Cambiar precio
    @PostMapping("/change-price")
    public Product changePrice(@RequestParam long id, @RequestParam double price) {
        System.out.println("POST: api/productos/change-price");
        return productService.changePrice(id, price);
    }

    
    // Obtener TODOS los productos
    @GetMapping
    public List<Product> getAllProducts() {
        System.out.println("GET: api/productos/");
        return productService.getAllProducts();
    }

    // Obtener producto
    @GetMapping("/{id}")
    public Product obtainProduct(@PathVariable Long id) {
        System.out.println("GET: api/productos/"+id.toString());
        return productService.obtainProduct(id);
    }

//    @PostMapping("/{id}/imagen")
//    public ResponseEntity<Product> subirImagen(
//            @PathVariable Long id,
//            @RequestParam("file") MultipartFile file
//    ) {
//        System.out.println("POST: api/productos/" + id + "/imagen");
//        Product actualizado = productService.subirImagen(id, file);
//        return ResponseEntity.ok(actualizado);
//    }

    @PostMapping("/{id}/imagen")
    public ResponseEntity<String> subirImagen(
            @PathVariable Long id,
            @RequestPart("file") MultipartFile file) throws Exception {

        Imagen saved = imageService.create(file);
        productService.asociarImagen(id, saved.getId()); // implementá esta asociación
        return ResponseEntity.ok("created:" + saved.getId());
    }
}
