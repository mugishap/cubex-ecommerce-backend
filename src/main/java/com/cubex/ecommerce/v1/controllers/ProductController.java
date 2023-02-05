package com.cubex.ecommerce.v1.controllers;

import com.cubex.ecommerce.v1.dtos.CreateProductDTO;
import com.cubex.ecommerce.v1.models.Product;
import com.cubex.ecommerce.v1.payload.ApiResponse;
import com.cubex.ecommerce.v1.serviceImpls.ProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody CreateProductDTO dto) {
        Product product = new Product();
        product.setCurrency(dto.getCurrency());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        if (!Objects.equals(dto.getImage(), "")) {
            product.setImage(dto.getImage());
        }
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/product").toString());
        return ResponseEntity.created(uri).body(new ApiResponse(true, "Product created successfully", product));
    }
}
