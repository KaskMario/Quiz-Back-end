package com.example.quizApp.service;

import com.example.quizApp.dao.RoleRepo;
import com.example.quizApp.dao.UserRepo;
import com.example.quizApp.model.Role;
import com.example.quizApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import static com.example.quizApp.model.RoleName.ROLE_USER;

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
        Role userRole = roleRepo.findByRole(ROLE_USER)
                .orElseThrow(() -> new RuntimeException("User Role not set."));
        newUser.setRole(userRole);


        return userRepo.save(newUser);
    }


    public void deleteUser(int userId){
        User deletedUser = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        userRepo.delete(deletedUser);

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

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority (user.getRole().getRole().toString());
                /*.map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());*/

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.singletonList(authority));
    }

    public List<Role> getRoles() {
        return roleRepo.findAll();
    }

    public User getUser(String username){
        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }



}

