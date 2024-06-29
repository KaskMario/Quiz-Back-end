package com.example.quizApp.controller;

import com.example.quizApp.model.RoleName;
import com.example.quizApp.model.User;
import com.example.quizApp.service.JwtTokenService;
import com.example.quizApp.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
                .map(userId -> {
                    String roleName = user.getRoles().stream()
                            .findFirst()
                            .map(role -> role.getRole().name())
                            .orElse("USER");
                    String token = jwtTokenService.generateToken(userId, roleName.replace("ROLE_", ""));
                    return new ResponseEntity<>(token, HttpStatus.OK); // Return token without "Bearer"
                }).orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }


    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {

        return loginService.register(user, RoleName.ROLE_USER)
                .map(u -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "User registered successfully");
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
                .map(u -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Admin registered successfully");
                    return new ResponseEntity<>(response, HttpStatus.CREATED);
                })
                .orElseGet(() -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "User already exists");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                });
    }

}

