package com.example.demo.repos;

import com.example.demo.entitety.AppUser;
import com.example.demo.entitety.RcUserDonor;
import com.example.demo.entitety.UserCity;
import com.example.demo.helpers.enums.BloodTypes;
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

  @Query("SELECT a FROM RcUserDonor a WHERE  a.appUser.bloodType IN :types " +
              "AND a.address.city = :userCity " +
              "AND a.dateLastBloodGiving < :blood " +
              "AND a.dateLastPlateletsGiving < :platelets " +
              "AND a.dateLastBloodPlasmaGiving < :bloodPlasma " +
              "AND a.sentNotification = false")
  Optional<List<RcUserDonor>> findByBloodType(
              List<BloodTypes> types,
              UserCity userCity,
              LocalDateTime blood,
              LocalDateTime platelets,
              LocalDateTime bloodPlasma
  );
}
