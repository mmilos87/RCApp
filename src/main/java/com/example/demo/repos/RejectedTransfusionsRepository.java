package com.example.demo.repos;

import com.example.demo.entitety.RejectedTransfusions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RejectedTransfusionsRepository extends JpaRepository<RejectedTransfusions,Long> {
}
