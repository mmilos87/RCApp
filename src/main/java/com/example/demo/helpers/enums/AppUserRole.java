package com.example.demo.helpers.enums;

import static com.example.demo.helpers.enums.AppUserPermission.REGISTRATION_READ;
import static com.example.demo.helpers.enums.AppUserPermission.REGISTRATION_WRITE;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum AppUserRole {
  USER(Sets.newHashSet(REGISTRATION_READ)),
  ADMIN(Sets.newHashSet(REGISTRATION_WRITE, REGISTRATION_READ));
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
