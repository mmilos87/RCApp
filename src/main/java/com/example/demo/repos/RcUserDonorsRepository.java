package com.example.demo.repos;

import com.example.demo.entitety.AppUser;
import com.example.demo.entitety.RcUserDonor;
import com.example.demo.entitety.UserCity;
import com.example.demo.helpers.enums.BloodTypes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface RcUserDonorsRepository extends JpaRepository<RcUserDonor, Long> {

  Optional<RcUserDonor> findByAppUser(AppUser appUser);

  @Query(
      "SELECT a FROM RcUserDonor a WHERE a.appUser <> :appUser AND a.appUser.bloodType IN :types " +
          "AND a.address.userCity = :userCity AND a.sentNotification = false " +
          "AND (a.dateLastBloodGiving IS NULL OR a.dateLastBloodGiving < :blood) " +
          "AND (a.dateLastPlateletsGiving IS NULL OR a.dateLastPlateletsGiving < :platelets) " +
          "AND (a.dateLastBloodPlasmaGiving IS NULL OR a.dateLastBloodPlasmaGiving < :bloodPlasma)")
  Optional<List<RcUserDonor>> findByBloodType(
      List<BloodTypes> types,
      UserCity userCity,
      LocalDateTime blood,
      LocalDateTime platelets,
      LocalDateTime bloodPlasma,
      AppUser appUser, Pageable page
  );
}
