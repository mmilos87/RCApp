package com.example.demo.repos;

import com.example.demo.entitety.AppUser;
import com.example.demo.entitety.RcUserMedic;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
@Transactional(readOnly = true)
public interface RcUserMedicRepository extends JpaRepository<RcUserMedic,Long> {

  Optional<RcUserMedic> findByAppUser(AppUser appUser);
}

