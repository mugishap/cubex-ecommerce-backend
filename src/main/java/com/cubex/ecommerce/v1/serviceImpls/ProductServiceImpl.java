package com.cubex.ecommerce.v1.serviceImpls;

import com.cubex.ecommerce.v1.dtos.CreateProductDTO;
import com.cubex.ecommerce.v1.models.Cart;
import com.cubex.ecommerce.v1.models.Product;
import com.cubex.ecommerce.v1.repositories.ICartRepository;
import com.cubex.ecommerce.v1.repositories.IProductRepository;
import com.cubex.ecommerce.v1.services.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;
    private final ICartRepository cartRepository;
    private final UserServiceImpl userService;

    @Autowired
    public ProductServiceImpl(IProductRepository productRepository, ICartRepository cartRepository, UserServiceImpl userService) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;
    }

    @Override
    public Product createProduct(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    public Product updateProduct(UUID productId, CreateProductDTO product) {
        Optional<Product> product1 = this.productRepository.findById(productId);
        if (product1.isPresent()) {
            Product updatedProduct = product1.get();
            updatedProduct.setImage(product.getImage());
            updatedProduct.setName(product.getName());
            updatedProduct.setPrice(product.getPrice());
            updatedProduct.setCurrency(product.getCurrency());
            return this.productRepository.save(updatedProduct);
        } else {
            return null;
        }
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
        for (UUID id : productIds) {
            this.productRepository.deleteById(id);
        }
        return "Products deleted sucessfully";
    }

    @Override
    public List<Product> searchProduct(String query) {
        return this.productRepository.findProductsByQuery(query);
    }

    @Override
    public String truncate() {
        this.productRepository.deleteAll();
        return "Products deleted Successfully";
    }

    @Override
    public Page<Product> getProductsPaginated(Pageable pageable) {
        return this.productRepository.findAll(pageable);
    }

    @Override
    public Cart addProductToCart(Product product, Cart cart) {
        cart.getProducts().add(product);
        this.cartRepository.save(cart);
        return cart;
    }

    @Override
    public Cart addMultipleProductsToCart(List<Product> products, Cart cart) {
        for (Product product : products) {
            cart.getProducts().add(product);
        }
        this.cartRepository.save(cart);
        return cart;
    }

    @Override
    public Cart removeProductFromCart(UUID productID, Cart cart) {
        cart.getProducts().remove(this.productRepository.getById(productID));
        this.cartRepository.save(cart);
        return cart;
    }


    @Override
    public Cart emptyCart() {
        Cart cart = this.userService.getLoggedInUser().getCart();

        cart.setProducts(new ArrayList<Product>());
        this.cartRepository.save(cart);
        return cart;
    }

    @Override
    public Cart removeMultipleItemsFromCart(List<UUID> productIds) {
        Cart cart = this.userService.getLoggedInUser().getCart();
        for (UUID productId : productIds) {
            cart.getProducts().remove(this.productRepository.getById(productId));
        }
        this.cartRepository.save(cart);
        return cart;
    }


}
