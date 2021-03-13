package com.example.demo.repos;

import com.example.demo.entitety.AppUser;
import com.example.demo.entitety.RcUserDonor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface RcUserDonorsRepository extends JpaRepository<RcUserDonor,Long> {

  Optional<RcUserDonor> findByAppUser(AppUser appUser);




}
