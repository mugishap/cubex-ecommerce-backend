package com.cubex.ecommerce.v1.dtos;


import com.cubex.ecommerce.v1.enums.EGender;
import com.cubex.ecommerce.v1.enums.ERole;
import com.cubex.ecommerce.v1.security.ValidPassword;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class SignUpDTO {

    @Email
    private  String email;

    @NotBlank
    private  String firstName;

    @NotBlank(message = "last name should not be null")
    private  String lastName;

    @NotBlank
    private  String mobile;

    private EGender gender;

    private ERole role;

    @ValidPassword
    private  String password;
}
