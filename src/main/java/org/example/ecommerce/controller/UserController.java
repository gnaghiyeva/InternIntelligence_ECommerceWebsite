package org.example.ecommerce.controller;


import org.example.ecommerce.dtos.user.LoginDto;
import org.example.ecommerce.dtos.user.RegisterDto;
import org.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> register(@ModelAttribute RegisterDto registerDto) {
        return ResponseEntity.ok(userService.registerUser(registerDto));
    }

    @PostMapping(value = "/login", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> login(@ModelAttribute LoginDto loginDto) {
        return ResponseEntity.ok(userService.loginUser(loginDto));
    }
}
