package com.example.hotelbooking.service;

import com.example.hotelbooking.entity.User;
import com.example.hotelbooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        // Зашифруйте пароль перед сохранением
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean checkRole(User user, String roleName) {
        return user.getRoles().stream().anyMatch(role -> role.getName().equals(roleName));
    }
}
