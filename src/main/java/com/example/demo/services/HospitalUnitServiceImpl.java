package com.example.demo.services;

import com.example.demo.entitety.HospitalUnit;
import com.example.demo.repos.HospitalUnitRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HospitalUnitServiceImpl implements HospitalUnitService{

  private HospitalUnitRepository hospitalUnitRepository;

  @Override
  public String registerNewHospitalUnit(HospitalUnit hospitalUnit) {
    boolean isHuExist = hospitalUnitRepository
        .findByHospitalUnitName(hospitalUnit.getHospitalUnitName()).isPresent();
    if (!isHuExist) {
      hospitalUnitRepository.save(hospitalUnit);
      return "registered";
    }
    return "hospital Unit exist";

  }

  @Override
  public HospitalUnit getHosUnit(String name) {
   return hospitalUnitRepository.findByHospitalUnitName(name).get();
  }
}
