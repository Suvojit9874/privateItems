package com.example.demo.repo;


import com.example.demo.model.auth.Role;
import com.example.demo.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    Optional<User> findByRoleAndUserId(Role role,Integer userId);
    List<User> findByRole(Role role);


}
