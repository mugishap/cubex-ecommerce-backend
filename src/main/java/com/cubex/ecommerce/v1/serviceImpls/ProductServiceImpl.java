package com.cubex.ecommerce.v1.serviceImpls;

import com.cubex.ecommerce.v1.models.Product;
import com.cubex.ecommerce.v1.services.IProductService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductServiceImpl  implements IProductService {
    @Override
    public Product createProduct() {
        return null;
    }

    @Override
    public Product updateProduct(Product product) {
        return null;
    }

    @Override
    public String deleteProduct(UUID id) {
        return null;
    }

    @Override
    public Optional<Product> getProductById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Product> getProducts() {
        return null;
    }
}
