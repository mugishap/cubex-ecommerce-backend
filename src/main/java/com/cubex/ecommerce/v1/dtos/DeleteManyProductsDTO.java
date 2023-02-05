package com.cubex.ecommerce.v1.dtos;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class DeleteManyProductsDTO {
    private List<UUID> productIds;
}
