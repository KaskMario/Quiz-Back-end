package com.example.quizApp.controller;

import com.example.quizApp.model.Role;
import com.example.quizApp.model.RoleName;
import com.example.quizApp.model.User;
import com.example.quizApp.service.JwtTokenService;
import com.example.quizApp.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtTokenService jwtTokenService;
    private final LoginService loginService;
    private static final String ADMIN_KEY = "ventilaator";

    public AuthController(JwtTokenService jwtTokenService, LoginService loginService) {
        this.jwtTokenService = jwtTokenService;
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        return loginService.login(user)
                .map(loggedInUser -> {
                    Role role = loggedInUser.getRole();
                    if (role == null) {
                        System.out.println("User " + loggedInUser.getUsername() + " has no roles assigned");
                    } else {
                        System.out.println("User " + loggedInUser.getUsername() + " roles: " + role);
                    }
                    List<String> roles = Collections.singletonList(role.getRole().name());
                    String token = jwtTokenService.generateToken(loggedInUser, roles);

                    if (role.getRole().equals(RoleName.ROLE_ADMIN)) {
                        System.out.println("Admin user logged in: " + loggedInUser.getUsername());
                    }

                    return new ResponseEntity<>(token, HttpStatus.OK);
                }).orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        return loginService.register(user, RoleName.ROLE_USER)
                .map(registeredUser -> {
                    List<String> roles = Collections.singletonList(registeredUser.getRole().getRole().name());
                    String token = jwtTokenService.generateToken(registeredUser, roles);

                    Map<String, String> response = new HashMap<>();
                    response.put("message", "User registered successfully");
                    response.put("token", token);
                    return new ResponseEntity<>(response, HttpStatus.CREATED);
                })
                .orElseGet(() -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "User already exists");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                });
    }

    @PostMapping("/register-admin")
    public ResponseEntity<Map<String, String>> registerAdmin(@RequestBody User user, @RequestParam String adminKey) {
        if (!ADMIN_KEY.equals(adminKey)) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Invalid admin key");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        return loginService.register(user, RoleName.ROLE_ADMIN)
                .map(registeredAdmin -> {
                    List<String> roles = Collections.singletonList(registeredAdmin.getRole().getRole().name());
                    String token = jwtTokenService.generateToken(registeredAdmin, roles);

                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Admin registered successfully");
                    response.put("token", token);
                    return new ResponseEntity<>(response, HttpStatus.CREATED);
                })
                .orElseGet(() -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "User already exists");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                });
    }
}
