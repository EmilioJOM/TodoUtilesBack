package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    // Listar con filtros básicos: q (texto en descripción) o categoriaId (Long)
    public List<Product> listar(String q, Long categoriaId) {
        if (q != null && !q.isBlank()) {
            return repo.findByDescriptionContainingIgnoreCase(q);
        }
        if (categoriaId != null) {
            return repo.findByCategories_Id(categoriaId);
        }
        return repo.findAll();
    }

    public Product obtener(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public Product crear(Product p) {
        validarBasico(p);
        return repo.save(p);
    }

    public Product actualizar(Long id, Product p) {
        Product db = obtener(id);
        if (p.getDescription() != null)
            db.setDescription(p.getDescription());
        if (p.getPrice() > 0)
            db.setPrice(p.getPrice());
        if (p.getCategories() != null)
            db.setCategories(p.getCategories());
        validarBasico(db);
        return repo.save(db);
    }

    public void borrar(Long id) {
        repo.deleteById(id);
    }

    private void validarBasico(Product p) {
        if (p.getDescription() == null || p.getDescription().isBlank()) {
            throw new RuntimeException("Descripción requerida");
        }
        if (p.getPrice() <= 0) {
            throw new RuntimeException("Precio debe ser > 0");
        }
    }
}
