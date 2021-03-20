package com.example.demo.helpers.enums;

public enum AppUserPermission {

  DONOR_READ("donor:read"),
  DONOR_WRITE("donor:write"),
  MEDIC_READ("medic:read"),
  MEDIC_WRITE("medic:read"),
  RECIPIENT_READ("recipient:read"),
  RECIPIENT_WRITE("recipient:write"),
  HOSPITAL_UNIT_READ("hospitalUnit:read"),
  HOSPITAL_UNIT_WRITE("hospitalUnit:write"),
  TRANSFUSION_QUERY_READ("transfusionQuery:read"),
  TRANSFUSION_QUERY_WRITE("transfusionQuery:write");

  private final String permission;

  AppUserPermission(String permission) {
    this.permission = permission;
  }
  public String getPermission() {
    return permission;
  }
}
