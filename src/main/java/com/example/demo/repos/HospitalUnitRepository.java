package com.example.demo.repos;

import com.example.demo.entitety.AppUser;
import com.example.demo.entitety.HospitalUnit;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface HospitalUnitRepository extends JpaRepository<HospitalUnit, Long> {

  Optional<HospitalUnit> findByHospitalUnitName(String hospitalUnitName);

}
