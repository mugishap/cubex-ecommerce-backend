package com.cubex.ecommerce.v1.services;

import com.cubex.ecommerce.v1.models.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProductService {

    public Product createProduct(Product product);

    public Product updateProduct(Product product);

    public String deleteProduct(UUID id);

    public Product getProductById(UUID id);

    public List<Product> getProducts();

    public Product buyProduct(UUID id);

    public String deleteManyProducts(List<UUID> productIds);

    public List<Product> searchProduct(String query);

}
