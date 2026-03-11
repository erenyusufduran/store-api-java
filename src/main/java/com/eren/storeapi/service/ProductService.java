package com.eren.storeapi.service;

import com.eren.storeapi.entity.Product;
import com.eren.storeapi.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(String name, BigDecimal price) {
        Product product = new Product(
                UUID.randomUUID(),
                name,
                price
        );

        return productRepository.save(product);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}