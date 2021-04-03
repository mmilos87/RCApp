package com.example.demo.repos;

import com.example.demo.entitety.UserCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCItyRepository extends JpaRepository<UserCity,Long> {
}
