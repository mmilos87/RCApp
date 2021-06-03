package com.example.demo.repos;


import com.example.demo.entitety.AppUser;
import com.example.demo.entitety.LoginAttempts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginAttemptRepo extends JpaRepository<LoginAttempts,Long> {
    Optional<LoginAttempts> findByAppUser(AppUser appUser);
}
