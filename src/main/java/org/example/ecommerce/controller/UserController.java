package org.example.ecommerce.controller;


import org.example.ecommerce.dtos.user.LoginDto;
import org.example.ecommerce.dtos.user.RegisterDto;
import org.example.ecommerce.dtos.user.UpdateRoleDto;
import org.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/change-role", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> changeUserRole(@ModelAttribute UpdateRoleDto updateRoleDto){
        String reponse = userService.updateUserRole(updateRoleDto);
        return ResponseEntity.ok(reponse);
    }
}
