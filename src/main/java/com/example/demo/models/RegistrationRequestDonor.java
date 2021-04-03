package com.example.demo.models;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class RegistrationRequestDonor  {

  private RegistrationRequestAppUser requestAppUser;
  private Long numberOfBloodGiving;
  private Long numberOfPlateletsGiving;
  private RegistrationRcAddress registrationRcAddress;


}
