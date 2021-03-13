package com.example.demo.helpers.enums;

public enum AppLinks {
  CONFIRM_REGISTRATION_LINK("http://localhost:8080/api/v1/registration/confirm?token=");
  private final String link;

  AppLinks(String link) {
    this.link = link;
  }

  public String getLink() {
    return link;
  }
}
