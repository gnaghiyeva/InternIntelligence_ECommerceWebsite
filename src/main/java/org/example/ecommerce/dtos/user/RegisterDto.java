package org.example.ecommerce.dtos.user;

import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String email;
    private String password;
}
