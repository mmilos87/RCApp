package com.example.demo.helpers.enums;

public enum AppUserPermission {
  REGISTRATION_READ("registration:read"),
  REGISTRATION_WRITE("registration:write");
  private final String permission;

  AppUserPermission(String permission) {
    this.permission = permission;
  }
  public String getPermission() {
    return permission;
  }
}
