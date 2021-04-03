package com.example.demo.repos;

import com.example.demo.entitety.RejectedTransfusions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RejectedTransfusionsRepository extends JpaRepository<RejectedTransfusions,Long> {
}
