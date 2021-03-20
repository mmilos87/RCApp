package com.example.demo.services;

import com.example.demo.entitety.HospitalUnit;
import javax.servlet.http.HttpServletRequest;

public interface HospitalUnitService  {

  String registerNewHospitalUnit(HospitalUnit  hospitalUnit);
  HospitalUnit getHosUnit(String name);
  HospitalUnit extractAppUserFromRequest(HttpServletRequest request);
}
