package com.example.demo.helpers.enums;

public enum AppMessages {
  USER_WITH_THAT_EMAIL_NOT_FOUND("user with email %s not found"),

  EMAIL_IS_ALREADY_TAKEN("email already taken"),

  EMAIL_IS_NOT_VALID("email is not valid"),

  TOKEN_NOT_FOUND("token not found"),

  TOKEN_EXPIRED("token EXPIRED"),

  TOKEN_ALREADY_CONFIRMED("email already confirmed"),

  JMBG_IS_TOO_LONG("jmbg is not valid, jmbg must have 13 characters"),

  JMBG_CHECKSUM_IS_NOT_CORRECT("jmbg is not valid, checksum is not correct"),

  JMBG_DATE_IS_NOT_VALID("jmbg is not valid, date is not valid"),

 USER_ALREADY_REGISTERED("User is already registered");

  private  final String message;
  AppMessages(String  message) {
    this.message =message;
  }

  public String getMessage() {
    return message;
  }
}
