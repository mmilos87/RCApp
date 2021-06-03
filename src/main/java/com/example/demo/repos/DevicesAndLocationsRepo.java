package com.example.demo.repos;

import com.example.demo.entitety.AppUser;
import com.example.demo.entitety.DevicesAndLocations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DevicesAndLocationsRepo extends JpaRepository<DevicesAndLocations,Long> {
   Optional<DevicesAndLocations> findByAppUser(AppUser appUser);
}
