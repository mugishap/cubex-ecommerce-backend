package com.cubex.ecommerce.v1.dtos;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class AdminConfirmationDTO {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
