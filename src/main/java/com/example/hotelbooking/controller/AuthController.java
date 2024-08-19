package com.example.hotelbooking.controller;

import com.example.hotelbooking.entity.User;
import com.example.hotelbooking.service.UserService;
import com.example.hotelbooking.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        // Найдите пользователя по имени
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Проверка пароля (если он совпадает)
        if (!user.getPassword().equals(password)) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }

        // Создание JWT токена
        String token = jwtUtil.generateToken(user.getUsername());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam String token, @RequestParam String username) {
        boolean isValid = jwtUtil.validateToken(token, username);

        Map<String, Boolean> response = new HashMap<>();
        response.put("isValid", isValid);

        return ResponseEntity.ok(response);
    }
}
