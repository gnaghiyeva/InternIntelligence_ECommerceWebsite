package org.example.ecommerce.service.impl;

import org.example.ecommerce.config.JwtUtil;
import org.example.ecommerce.dtos.user.LoginDto;
import org.example.ecommerce.dtos.user.RegisterDto;
import org.example.ecommerce.model.Role;
import org.example.ecommerce.model.User;
import org.example.ecommerce.repository.UserRepository;
import org.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String registerUser(RegisterDto registerDto) {
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            return "Email already exists!";
        }
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        return "User registered successfully!";
    }

    @Override
    public String loginUser(LoginDto loginDto) {
        Optional<User> user = userRepository.findByUsername(loginDto.getUsername());

        if (user.isPresent() && passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword())) {
            return jwtUtil.generateToken(user.get().getEmail());
        }
        return "Invalid credentials";
    }

    @Override
    public String getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(User::getUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

}
