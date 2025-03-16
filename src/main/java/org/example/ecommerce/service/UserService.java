package org.example.ecommerce.service;

import org.example.ecommerce.dtos.user.LoginDto;
import org.example.ecommerce.dtos.user.RegisterDto;
import org.example.ecommerce.model.User;

public interface UserService {
    String registerUser(RegisterDto registerDto);
    String loginUser(LoginDto loginDto);
    String getUserByUsername(String username);
}
