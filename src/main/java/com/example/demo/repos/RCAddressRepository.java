package com.example.demo.repos;

import com.example.demo.entitety.RcAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RCAddressRepository extends JpaRepository<RcAddress,Long> {
}
