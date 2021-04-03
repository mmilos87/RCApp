package com.example.demo.models;

import com.example.demo.entitety.AppUser;
import com.example.demo.exception.JmbgIsNotValidException;
import com.example.demo.helpers.classes.UniqueMasterCitizenNumberHelper;
import com.example.demo.helpers.enums.AppUserRole;
import com.example.demo.helpers.enums.BloodTypes;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequestAppUser {
  private final Long jmbg;
  private final String firstName;
  private final String lastName;
  private final String email;
  private final String password;
  private BloodTypes bloodType;

  public AppUser getAppUser() throws JmbgIsNotValidException {
    return AppUser.builder()
        .gender(new UniqueMasterCitizenNumberHelper(jmbg.toString()).getGender())
        .firstName(firstName)
        .bloodType(bloodType)
        .password(password)
        .appUserRole(AppUserRole.USER)
        .lastName(lastName)
        .email(email)
        .jmbg(jmbg)
        .build();
  }

}
