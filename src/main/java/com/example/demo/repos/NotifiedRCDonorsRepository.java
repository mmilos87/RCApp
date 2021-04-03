package com.example.demo.repos;

import com.example.demo.entitety.NotifiedRcDonors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotifiedRCDonorsRepository extends JpaRepository<NotifiedRcDonors, Long> {
}
