package com.example.demo.helpers.classes;

import com.example.demo.entitety.AppUser;
import com.example.demo.helpers.enums.AppUserRole;
import com.example.demo.helpers.enums.BloodTypes;
import com.example.demo.helpers.enums.GenderType;
import com.example.demo.helpers.enums.RCLoginAuthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OAuth2GoogleUserInfo {
    private String name;
    private String lastName;
    private String email;
    private String nonce;
    private String tempJmbg;

  public OAuth2GoogleUserInfo(Map<String, Object> attributes) {
    this.setEmail((String) attributes.get("email"));
    this.setName((String) attributes.get("given_name"));
    this.setLastName((String) attributes.get("family_name"));
    this.setNonce((String) attributes.get("nonce"));
    this.setTempJmbg((String) attributes.get("sub"));
  }

    public AppUser gelLeagueUser(){
    return AppUser.builder()
        .firstName(name)
        .lastName(lastName)
        .password(nonce)
        .email(email)
        .jmbg(Long.getLong(tempJmbg))
        .enabled(true)
        .bloodType(BloodTypes.NOT_CHECKED)
        .gender(GenderType.NOT_CHECKED)
        .appUserRole(AppUserRole.USER)
        .authProvider(RCLoginAuthProvider.GOOGLE)
        .build();
  }
}
