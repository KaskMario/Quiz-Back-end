package com.example.quizApp.dao;

import com.example.quizApp.model.Role;
import com.example.quizApp.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    Optional<Role>findByRole(RoleName role);
}
