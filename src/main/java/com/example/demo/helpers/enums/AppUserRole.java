package com.example.demo.helpers.enums;

import static com.example.demo.helpers.enums.AppUserPermission.DONOR_READ;
import static com.example.demo.helpers.enums.AppUserPermission.DONOR_WRITE;
import static com.example.demo.helpers.enums.AppUserPermission.HOSPITAL_UNIT_READ;
import static com.example.demo.helpers.enums.AppUserPermission.HOSPITAL_UNIT_WRITE;
import static com.example.demo.helpers.enums.AppUserPermission.MEDIC_READ;
import static com.example.demo.helpers.enums.AppUserPermission.MEDIC_WRITE;
import static com.example.demo.helpers.enums.AppUserPermission.RECIPIENT_READ;
import static com.example.demo.helpers.enums.AppUserPermission.RECIPIENT_WRITE;
import static com.example.demo.helpers.enums.AppUserPermission.TRANSFUSION_QUERY_READ;
import static com.example.demo.helpers.enums.AppUserPermission.TRANSFUSION_QUERY_WRITE;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum AppUserRole {
  USER(Sets.newHashSet(DONOR_READ, HOSPITAL_UNIT_READ, TRANSFUSION_QUERY_READ)),
  USER_MEDIC(Sets.newHashSet(RECIPIENT_READ, RECIPIENT_WRITE, DONOR_WRITE,
      DONOR_READ, MEDIC_READ, TRANSFUSION_QUERY_READ, TRANSFUSION_QUERY_WRITE, HOSPITAL_UNIT_READ)),
  ADMIN(Sets.newHashSet(DONOR_READ, DONOR_WRITE, RECIPIENT_READ, RECIPIENT_WRITE, MEDIC_READ,
      MEDIC_WRITE, TRANSFUSION_QUERY_READ, TRANSFUSION_QUERY_WRITE, HOSPITAL_UNIT_READ,
      HOSPITAL_UNIT_WRITE)),
  ADMIN_MEDIC(Sets.newHashSet(RECIPIENT_READ, RECIPIENT_WRITE, DONOR_WRITE, DONOR_READ, MEDIC_READ,
      MEDIC_WRITE, TRANSFUSION_QUERY_READ, TRANSFUSION_QUERY_WRITE, HOSPITAL_UNIT_READ));
  private final Set<AppUserPermission> permissionSet;

  AppUserRole(Set<AppUserPermission> permissionSet) {
    this.permissionSet = permissionSet;
  }

  public Set<AppUserPermission> getPermissionSet() {
    return permissionSet;
  }

public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
  Set<SimpleGrantedAuthority> authorities = getPermissionSet().stream()
      .map(p -> new SimpleGrantedAuthority(p.getPermission()))
      .collect(Collectors.toSet());
  authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
  return authorities;
}
}
