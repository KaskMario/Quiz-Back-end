/*
package com.example.quizApp.service;


import com.example.quizApp.dao.RoleRepo;
import com.example.quizApp.dao.UserRepo;
import com.example.quizApp.model.Role;
import com.example.quizApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public User saveNewUser(String username, String password){
        User newUser = new User();
        if(userRepo.findByUsername(username).isEmpty()){
            newUser.setUsername(username);
            newUser.setPassword(passwordEncoder.encode(password));
        }
        Role userRole = roleRepo.findByRole(userRole)
                .orElseThrow(() -> new RuntimeException("User Role not set."));
        newUser.getRoles().add(userRole);


        return userRepo.save(newUser);
    }

    public void deleteUser(User user){
        userRepo.delete(user);

    }

    public User updateUser(User user){
        return userRepo.save(user);
    }


    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}

*/
