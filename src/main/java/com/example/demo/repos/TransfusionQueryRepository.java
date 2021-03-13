package com.example.demo.repos;

import com.example.demo.entitety.TransfusionQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransfusionQueryRepository extends JpaRepository<TransfusionQuery,Long> {

}
