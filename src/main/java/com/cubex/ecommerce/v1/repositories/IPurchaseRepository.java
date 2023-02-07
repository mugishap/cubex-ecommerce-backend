package com.cubex.ecommerce.v1.repositories;

import com.cubex.ecommerce.v1.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IPurchaseRepository extends JpaRepository<Product, UUID> {
}
