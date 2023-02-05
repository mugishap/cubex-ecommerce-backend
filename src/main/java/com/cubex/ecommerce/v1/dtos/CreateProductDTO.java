package com.cubex.ecommerce.v1.dtos;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CreateProductDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String currency;

    @NotBlank
    private int price;

    private String image;

}
