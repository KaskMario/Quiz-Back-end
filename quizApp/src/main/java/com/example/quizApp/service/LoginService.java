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
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser.isPresent() && passwordEncoder.matches(user.getPassword(), foundUser.get().getPassword())) {
            user.setRole(foundUser.get().getRole());
            return Optional.of(foundUser.get().getId());
        }
        return Optional.empty();
    }

   public Optional<User> register(User user, RoleName roleName) {
       if (userRepository.findByUsername(user.getUsername()).isPresent()) {
           return Optional.empty();
       }

       user.setPassword(passwordEncoder.encode(user.getPassword()));
       Role role = roleRepository.findByRole(roleName)
               .orElseThrow(() -> new RuntimeException("Role not found"));
       user.setRole(role);

       return Optional.of(userRepository.save(user));
   }


    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
    }

