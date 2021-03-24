package com.example.demo.repos;

import com.example.demo.entitety.AppUser;
import com.example.demo.entitety.TransfusionQuery;
import com.example.demo.helpers.enums.TransfusionTypes;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransfusionQueryRepository extends JpaRepository<TransfusionQuery,Long> {

  @Query("SELECT a FROM TransfusionQuery a WHERE a.recipient = ?1 and a.transfusionType=?2")
  Optional<TransfusionQuery> findByRecipientBYType(AppUser appUser, TransfusionTypes types);

}
