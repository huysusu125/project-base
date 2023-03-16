package com.huytd.basecacheredis.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @Email(message = "email is invalid")
    @Size(min = 6, max = 255, message = "email must be between {min} and {max} characters long")
    private String email;

    @Size(min = 8, max = 255, message = "password must be between {min} and {max} characters long")
    @NotBlank(message = "password is not empty")
    private String password;

}
