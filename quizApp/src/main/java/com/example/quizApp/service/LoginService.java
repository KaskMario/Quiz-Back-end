package com.example.quizApp.service;

import com.example.quizApp.dao.RoleRepo;
import com.example.quizApp.dao.UserRepo;
import com.example.quizApp.model.Role;
import com.example.quizApp.model.RoleName;
import com.example.quizApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private RoleRepo roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Optional<Integer> login(User user) {
        System.out.println("Login attempt for user: " + user.getUsername());
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser.isPresent()) {
            System.out.println("User found: " + existingUser.get().getUsername());
            boolean passwordMatch = passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword());
            System.out.println("Password match: " + passwordMatch);
            if (passwordMatch) {
                return Optional.of(existingUser.get().getId());
            }
        } else {
            System.out.println("User not found");
        }
        return Optional.empty();
    }

    public Optional<User> register(User user, RoleName roleName) {
      //  System.out.println("Checking if user exists: " + user.getUsername());
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
           // System.out.println("User already exists: " + user.getUsername());
            return Optional.empty(); // User already exists
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByRole(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoles().add(role);
        return Optional.of(userRepository.save(user));
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
    }

