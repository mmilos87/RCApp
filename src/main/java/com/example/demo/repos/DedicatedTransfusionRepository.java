package com.example.demo.repos;

import com.example.demo.entitety.DedicatedTransfusions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DedicatedTransfusionRepository extends JpaRepository<DedicatedTransfusions,Long> {
}
