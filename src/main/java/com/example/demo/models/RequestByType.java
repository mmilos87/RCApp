package com.example.demo.models;

import com.example.demo.helpers.enums.*;
import lombok.Getter;

@Getter
public class RequestByType {

  private TransfusionTypes transfusionType;
  private BloodTypes bloodType;
  private GenderType genderType;
  private MedicTitle medicTitle;
  private AppUserRole role;
}
