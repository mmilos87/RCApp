package com.example.demo.helpers.enums;

public enum BloodTypes {
  A_POSITIVE("A+"),
  A_NEGATIVE("A-"),
  B_POSITIVE("B+"),
  B_NEGATIVE("B-"),
  O_POSITIVE("O+"),
  O_NEGATIVE("O-"),
  AB_POSITIVE("AB+"),
  AB_NEGATIVE("AB-"),
  NOT_CHECKED("NOT_CHECKED");
  private final String bloodType;

  BloodTypes(String  bloodType) {
    this.bloodType=bloodType;
  }

  public String getBloodType() {
    return bloodType;
  }
}
