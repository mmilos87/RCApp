package com.example.demo.helpers.enums;


public enum JwtTokenFields {
  JMBG("jmbg"),
  FIRST_NAME("firstName"),
  LAST_NAME("lastName"),
  GENDER_TYPE("gender"),
  EMAIL("email"),
  PASSWORD("password"),
  BLOOD_TYPE("bloodType"),
  IS_LOCKED("isLocked"),
  IS_ENABLED("isEnabled"),
  IS_BlOOD_CHECKED("isBloodChecked"),
  APP_USER_ROLE("appUserRole"),
  MEDIC_ID("medicId"),
  MEDIC_TITLE("medicTitle"),
  HOSPITAL_UNIT_ID("hospitalUnitId"),
  HOSPITAL_UNIT_NAME("hospitalUnitName"),
  HOSPITAL_UNIT_ADDRESS("hospitalUnitAddress");

  private String fieldName;

  public String getFieldName() {
    return fieldName;
  }

  JwtTokenFields(String fieldName) {
    this.fieldName = fieldName;
  }
}








