package com.cubex.ecommerce.v1.repositories;

import com.cubex.ecommerce.v1.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface IProductRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT p from Product p WHERE p.name LIKE ('%'||lower(:query)||'%')")
    List<Product> findProductsByQuery(String query);

    @Query("UPDATE Product p SET p.isBought=true WHERE p.id=:id")
    Product changeProductBoughtStatus(UUID id);

}
