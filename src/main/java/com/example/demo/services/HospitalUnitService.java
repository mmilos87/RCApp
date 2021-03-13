package com.example.demo.services;

import com.example.demo.entitety.HospitalUnit;

public interface HospitalUnitService  {

  String registerNewHospitalUnit(HospitalUnit  hospitalUnit);
  HospitalUnit getHosUnit(String name);
}
