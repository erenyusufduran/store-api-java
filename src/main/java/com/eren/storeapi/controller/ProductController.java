package com.eren.storeapi.controller;

import com.eren.storeapi.entity.Product;
import com.eren.storeapi.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductResponse createProduct(@Valid @RequestBody CreateProductRequest request) {
        Product product = productService.createProduct(
                request.name(),
                request.price()
        );

        return ProductResponse.from(product);
    }

    @GetMapping
    public List<ProductResponse> getProducts() {
        return productService.getProducts()
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    public record CreateProductRequest(
            @NotBlank(message = "name is required")
            String name,

            @DecimalMin(value = "0.01", message = "price must be greater than 0")
            BigDecimal price
    ) {
    }

    public record ProductResponse(
            UUID id,
            String name,
            BigDecimal price
    ) {
        public static ProductResponse from(Product product) {
            return new ProductResponse(
                    product.getId(),
                    product.getName(),
                    product.getPrice()
            );
        }
    }
}