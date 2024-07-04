package com.example.quizApp.security;

import com.example.quizApp.service.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    @Autowired
    public JwtRequestFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = request.getHeader("Authorization");
        System.out.println("Authorization header: " + token);  // Logging the authorization header

        if (token != null && token.startsWith("Bearer ")) {
            String tokenWithoutBearer = token.substring(7);
            System.out.println("Token without Bearer prefix: " + tokenWithoutBearer);

            try {
                if (tokenWithoutBearer.chars().filter(ch -> ch == '.').count() != 2) {
                    throw new IllegalArgumentException("JWT strings must contain exactly 2 period characters. Found: " + tokenWithoutBearer.chars().filter(ch -> ch == '.').count());
                }
                Authentication authentication = jwtTokenService.getAuthentication(tokenWithoutBearer);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("Authenticated user with token");
            } catch (Exception e) {
                System.err.println("Authentication failed: " + e.getMessage());
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }



}