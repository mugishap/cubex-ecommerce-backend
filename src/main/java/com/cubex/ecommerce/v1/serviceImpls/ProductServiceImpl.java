package com.cubex.ecommerce.v1.serviceImpls;

import com.cubex.ecommerce.v1.models.Product;
import com.cubex.ecommerce.v1.repositories.IProductRepository;
import com.cubex.ecommerce.v1.services.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    public String deleteProduct(UUID id) {
        this.productRepository.deleteById(id);
        return "Product deleted successfully";
    }

    @Override
    public Product getProductById(UUID id) {
        Optional<Product> productOptional = this.productRepository.findById(id);
        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            return null;
        }
    }

    @Override
    public List<Product> getProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public Product buyProduct(UUID id) {
        this.productRepository.changeProductBoughtStatus(id);
        return this.productRepository.getById(id);
    }

    @Override
    public String deleteManyProducts(List<UUID> productIds) {
        for (UUID id:productIds){
            this.productRepository.deleteById(id);
        }
        return "Products deleted sucessfully";
    }

    @Override
    public List<Product> searchProduct(String query) {
        return this.productRepository.findProductsByQuery(query);
    }
}
