package com.example.demo.repos;

import com.example.demo.entitety.AppUser;
import com.example.demo.entitety.HospitalUnit;
import com.example.demo.entitety.TransfusionQuery;
import com.example.demo.helpers.enums.BloodTypes;
import com.example.demo.helpers.enums.TransfusionTypes;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransfusionQueryRepository extends JpaRepository<TransfusionQuery, Long> {

  @Query("SELECT a FROM TransfusionQuery a WHERE a.recipient = ?1 and a.transfusionType=?2")
  Optional<TransfusionQuery> findByRecipientBYType(AppUser appUser, TransfusionTypes types);

  @Query("SELECT a FROM TransfusionQuery a WHERE a.hospitalUnit = ?1 and a.transfusionType=?2")
  Optional<List<TransfusionQuery>> findAllQueryOfHUnitByType(
      HospitalUnit hospitalUnit, TransfusionTypes types);

  Optional<List<TransfusionQuery>> findByTransfusionType(TransfusionTypes type);
  @Query("SELECT a FROM TransfusionQuery a WHERE a.recipient.bloodType = ?1")
  Optional<List<TransfusionQuery>> findByBloodType(BloodTypes type);
  Optional<List<TransfusionQuery>> findByHospitalUnit(HospitalUnit hospitalUnit);

}
