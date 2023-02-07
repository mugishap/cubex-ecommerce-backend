package com.cubex.ecommerce.v1.controllers;

import com.cubex.ecommerce.v1.models.Cart;
import com.cubex.ecommerce.v1.models.Product;
import com.cubex.ecommerce.v1.payload.ApiResponse;
import com.cubex.ecommerce.v1.repositories.ICartRepository;
import com.cubex.ecommerce.v1.repositories.IProductRepository;
import com.cubex.ecommerce.v1.serviceImpls.ProductServiceImpl;
import com.cubex.ecommerce.v1.serviceImpls.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final ICartRepository cartRepository;
    private final ProductServiceImpl productService;
    private final IProductRepository productRepository;
    private final UserServiceImpl userService;

    public CartController(ICartRepository cartRepository, ProductServiceImpl productService, IProductRepository productRepository, UserServiceImpl userService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @PostMapping("/add/multiple")
    public ResponseEntity<ApiResponse> addItemsToCart(@RequestBody List<UUID> productIds) {
        List<Product> products = new ArrayList<Product>();
        for (UUID productId : productIds) {
            products.add(this.productRepository.getById(productId));
        }
        Cart cart = this.userService.getLoggedInUser().getCart();
        return ResponseEntity.ok().body(new ApiResponse(true, "Products added successfully", this.productService.addMultipleProductsToCart(products, cart)));
    }

    @PostMapping("/add/:productId")
    public ResponseEntity<ApiResponse> addItemToCart(@PathVariable(name = "productId") UUID productId) {

        Cart cart = this.userService.getLoggedInUser().getCart();
        Product product = this.productRepository.getById(productId);
        return ResponseEntity.ok().body(new ApiResponse(true, "Product added successfully", this.productService.addProductToCart(product, cart)));
    }

    @DeleteMapping("/remove/:productId")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable(name = "productId") UUID productId) {
        Cart cart = this.userService.getLoggedInUser().getCart();
        return ResponseEntity.ok().body(new ApiResponse(true, "Product removed successfully", this.productService.removeProductFromCart(productId, cart)));
    }

    @DeleteMapping("/remove/multiple")
    public ResponseEntity<ApiResponse> removeMultipleItemsFromCart(@RequestBody List<UUID> productIds) {
        Cart cart = this.userService.getLoggedInUser().getCart();
        return ResponseEntity.ok().body(new ApiResponse(true, "Product removed successfully", this.productService.removeMultipleItemsFromCart(productIds)));
    }

    @DeleteMapping("/truncate")
    public ResponseEntity<ApiResponse> emptyCart() {
        return ResponseEntity.ok().body(new ApiResponse(true, "Cart emptied successfully", this.productService.emptyCart()));
    }
}
