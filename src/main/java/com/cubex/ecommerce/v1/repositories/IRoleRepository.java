package com.cubex.ecommerce.v1.repositories;

import com.cubex.ecommerce.v1.enums.ERole;
import com.cubex.ecommerce.v1.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IRoleRepository extends JpaRepository<Role, UUID> {

    @Query("SELECT r FROM Role r WHERE r.name=:role")
    Optional<Role> findByName(ERole role);
}
