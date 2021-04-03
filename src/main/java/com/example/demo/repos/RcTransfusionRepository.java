package com.example.demo.repos;

import com.example.demo.entitety.RcTransfusion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RcTransfusionRepository extends JpaRepository<RcTransfusion,Long> {

}
