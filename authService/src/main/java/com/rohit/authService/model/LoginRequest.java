package com.rohit.authService.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import jakarta.persistence.Entity;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank (message = "userName is required")
    private String username;
    @NotBlank (message = "password is required")
    private String password;
}
