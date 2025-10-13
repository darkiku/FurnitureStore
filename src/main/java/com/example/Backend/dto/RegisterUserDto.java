package com.example.Backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
    @NotBlank(message = "email cant be blank")
    @Email(message = "not valid email")
    private String email;
    @NotBlank(message = "password cant be blank")
    @Size(min = 6, message = "password should be at least 6 characters")
    private String password;
    @NotBlank(message = "username cant be blank")
    private String username;
}
