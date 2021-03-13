package com.example.demo.models;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class RegistrationRequestRecipient  {
  private RegistrationRequestAppUser requestAppUser;
  private RegistrationRequestHospitalUnit requestHospitalUnit;
}
