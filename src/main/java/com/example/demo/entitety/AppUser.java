package com.example.demo.entitety;

import com.example.demo.helpers.enums.AppUserRole;
import com.example.demo.helpers.enums.BloodTypes;
import com.example.demo.helpers.enums.GenderType;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class AppUser implements UserDetails {
  @Id
  private Long jmbg;
  @Column(nullable = false)
  private String firstName;
  @Column(nullable = false)
  private String lastName;
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private GenderType gender;
  @Column(nullable = false)
  private String email;
  @Column(nullable = false)
  private String password;
  @Enumerated(EnumType.STRING)
  private BloodTypes bloodType;
  @Enumerated(EnumType.STRING)
  private AppUserRole appUserRole;
  @Builder.Default
  private Boolean locked = false;
  @Builder.Default
  private Boolean enabled = false;
  @Builder.Default
  private Boolean isBloodChecked = false;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return appUserRole.getGrantedAuthorities();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  public String getLastName() {
    return lastName;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !locked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }
}
