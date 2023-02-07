package com.cubex.ecommerce.v1.controllers;

import com.cubex.ecommerce.v1.dtos.AdminConfirmationDTO;
import com.cubex.ecommerce.v1.dtos.CreateProductDTO;
import com.cubex.ecommerce.v1.dtos.DeleteManyProductsDTO;
import com.cubex.ecommerce.v1.models.Product;
import com.cubex.ecommerce.v1.payload.ApiResponse;
import com.cubex.ecommerce.v1.serviceImpls.ProductServiceImpl;
import com.cubex.ecommerce.v1.utils.Constants;
import io.swagger.annotations.Api;
import org.bouncycastle.ocsp.RespID;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.UUID;

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

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getProducts(){
     return ResponseEntity.ok().body(new ApiResponse(true,"Products fetched successfuly",this.productService.getProducts()));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse> getProductsPaginated(@RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
                                                            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit){

        Pageable pageable = (Pageable) PageRequest.of(page,limit, Sort.Direction.ASC,"id");

        return ResponseEntity.ok().body(new ApiResponse(true,"Products fetched successfully",this.productService.getProductsPaginated(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.ok().body(new ApiResponse(true, "Product fetched successfully", this.getProductById(id)));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable(name = "id") UUID id, @RequestBody CreateProductDTO dto) {
        return ResponseEntity.ok().body(new ApiResponse(true, "Product updated successfully", this.productService.updateProduct(id, dto)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.ok().body(new ApiResponse(true, "Product deleted succesfully", this.productService.deleteProduct(id)));
    }

    @DeleteMapping("/delete/multiple")
    public ResponseEntity<ApiResponse> deleteManyProducts(@RequestBody DeleteManyProductsDTO dto) {
        return ResponseEntity.ok().body(new ApiResponse(true, "Products deleted successfully", this.productService.deleteManyProducts(dto.getProductIds())));
    }

    @DeleteMapping("/truncate")
    public ResponseEntity<ApiResponse> deleteAllProducts(@RequestBody AdminConfirmationDTO dto){
        return ResponseEntity.ok().body(new ApiResponse(true, this.productService.truncate()));
    }
}
