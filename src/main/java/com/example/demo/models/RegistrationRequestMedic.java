package com.example.demo.models;


import com.example.demo.helpers.enums.MedicTitle;
import lombok.Getter;

@Getter
public class RegistrationRequestMedic {

  private RegistrationRequestAppUser registrationRequestAppUser;
  private MedicTitle title;
  private RegistrationRequestHospitalUnit requestHospitalUnit;
}
