package com.cubex.ecommerce.v1.repositories;

import com.cubex.ecommerce.v1.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ICartRepository extends JpaRepository<Cart, UUID> {
}
