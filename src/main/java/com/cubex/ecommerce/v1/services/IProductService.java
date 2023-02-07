package com.cubex.ecommerce.v1.services;

import com.cubex.ecommerce.v1.dtos.CreateProductDTO;
import com.cubex.ecommerce.v1.models.Cart;
import com.cubex.ecommerce.v1.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProductService {

    public Product createProduct(Product product);

    public Product updateProduct(UUID productId, CreateProductDTO product);

    public String deleteProduct(UUID id);

    public Product getProductById(UUID id);

    public List<Product> getProducts();

    public Product buyProduct(UUID id);

    public String deleteManyProducts(List<UUID> productIds);

    public List<Product> searchProduct(String query);

    public String truncate();

    public Page<Product> getProductsPaginated(Pageable pageable);

    public Cart addProductToCart(Product product, Cart cart);

    public Cart addMultipleProductsToCart(List<Product> products, Cart cart);

    public Cart removeProductFromCart(UUID productID, Cart cart);

    public Cart emptyCart();

    public Cart removeMultipleItemsFromCart(List<UUID> productIds);

}
