package com.example.demo.jwt;

import com.example.demo.entitety.*;
import com.example.demo.helpers.enums.*;
import com.example.demo.repos.RcUserMedicRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import static com.example.demo.helpers.enums.JwtTokenFields.*;

@AllArgsConstructor
@Component
public class JwtTokenHelper {

  private final JwtConfig jwtConfig;
  private final SecretKey secretKey;
  private final RcUserMedicRepository medicRepository;

  public String generateToken(Authentication authentication) {
    AppUser appUser = (AppUser) authentication.getPrincipal();
    return generateToken(appUser);
  }

  public String generateToken(AppUser appUser) {
    return createNewToken(buildClaims(appUser));
  }

  public String refreshToken(Claims claims){
    return createNewToken(claims);
  }

  public AppUser extractAppUserFromJwtClaims(Claims body) {
    AppUser appUser = AppUser.builder()
        .jmbg(body.get(JMBG.getFieldName(), Long.class))
        .firstName(body.get(FIRST_NAME.getFieldName(), String.class))
        .lastName(body.get(LAST_NAME.getFieldName(), String.class))
        .gender(GenderType.valueOf((String) body.get(GENDER_TYPE.getFieldName())))
        .email(body.get(EMAIL.getFieldName(), String.class))
        .password(body.get(PASSWORD.getFieldName(), String.class))
        .bloodType(BloodTypes.valueOf((String) body.get(BLOOD_TYPE.getFieldName())))
        .locked(body.get(IS_LOCKED.getFieldName(), Boolean.class))
        .enabled(body.get(IS_ENABLED.getFieldName(), Boolean.class))
        .isBloodChecked(body.get(IS_BlOOD_CHECKED.getFieldName(), Boolean.class))
        .appUserRole(AppUserRole.valueOf((String) body.get(APP_USER_ROLE.getFieldName())))
        .build();
    return appUser;
  }

  public HospitalUnit extractHospitalUnitFromJwtClaims(Claims body) {
    UserCity userCity=UserCity.builder()
            .id(body.get(HOSPITAL_UNIT_ADDRESS_CITY_ID.getFieldName(),Long.class))
            .cityName(body.get(HOSPITAL_UNIT_ADDRESS_CITY_NAME.getFieldName(),String.class))
            .build();
    RcAddress address=RcAddress.builder()
            .id(body.get(HOSPITAL_UNIT_ADDRESS_ID.getFieldName(), Long.class))
            .userCity(userCity)
            .township(body.get(HOSPITAL_UNIT_ADDRESS_TOWNSHIP.getFieldName(), String.class))
            .postalCodeZip(body.get(HOSPITAL_UNIT_ADDRESS_POSTAL_CODE_ZIP.getFieldName(),Long.class))
            .street(body.get(HOSPITAL_UNIT_ADDRESS_STREET.getFieldName(),String.class))
            .number(body.get(HOSPITAL_UNIT_ADDRESS_NUMBER.getFieldName(),String.class))
            .build();
    HospitalUnit hospitalUnit = HospitalUnit.builder()
        .id(body.get(HOSPITAL_UNIT_ID.getFieldName(), Long.class))
        .hospitalUnitName(body.get(HOSPITAL_UNIT_NAME.getFieldName(), String.class))
        .address(address)
        .build();
    return hospitalUnit;
  }

  public RcUserMedic extractMedicFromJwtClaims(Claims body) {
    RcUserMedic medic = RcUserMedic.builder()
        .hospitalUnit(extractHospitalUnitFromJwtClaims(body))
        .appUser(extractAppUserFromJwtClaims(body))
        .id(body.get(MEDIC_ID.getFieldName(), Long.class))
        .title(MedicTitle.valueOf((String) body.get(MEDIC_TITLE.getFieldName())))
        .build();
    return medic;
  }

  private String createNewToken(Claims claims) {
    long newDateInMillSec =
        new Date().getTime() + jwtConfig.getTokenExpirationAfterMinutes() * 60000;
    String token = Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(new Date())
        .setExpiration(new java.sql.Date(newDateInMillSec))
        .signWith(secretKey)
        .compact();
    return token;
  }
  private Claims buildClaims(AppUser appUser){
    Claims claims= Jwts.claims();
    claims.put(JMBG.getFieldName(), appUser.getJmbg());
    claims.put(FIRST_NAME.getFieldName(), appUser.getFirstName());
    claims.put(LAST_NAME.getFieldName(), appUser.getLastName());
    claims.put(GENDER_TYPE.getFieldName(), appUser.getGender().name());
    claims.put(EMAIL.getFieldName(), appUser.getEmail());
    claims.put(PASSWORD.getFieldName(), appUser.getPassword());
    claims.put(BLOOD_TYPE.getFieldName(), appUser.getBloodType().name());
    claims.put(IS_LOCKED.getFieldName(), appUser.getLocked());
    claims.put(IS_ENABLED.getFieldName(), appUser.getEnabled());
    claims.put(IS_BlOOD_CHECKED.getFieldName(), appUser.getIsBloodChecked());
    claims.put(APP_USER_ROLE.getFieldName(), appUser.getAppUserRole().name());
    switch (appUser.getAppUserRole()){
      case USER_MEDIC:
      case ADMIN_MEDIC:
        RcUserMedic medic= medicRepository.findByAppUser(appUser).get();
        claims.put(MEDIC_ID.getFieldName(),medic.getId());
        claims.put(MEDIC_TITLE.getFieldName(), medic.getTitle().name());
        claims.put(HOSPITAL_UNIT_ID.getFieldName(),medic.getHospitalUnit().getId());
        claims.put(HOSPITAL_UNIT_NAME.getFieldName(),medic.getHospitalUnit().getHospitalUnitName());
        claims.put(HOSPITAL_UNIT_ADDRESS_ID.getFieldName(),medic.getHospitalUnit().getAddress().getId());
        claims.put(HOSPITAL_UNIT_ADDRESS_CITY_ID.getFieldName(),medic.getHospitalUnit().getAddress().getUserCity().getId());
        claims.put(HOSPITAL_UNIT_ADDRESS_CITY_NAME.getFieldName(),medic.getHospitalUnit().getAddress().getUserCity().getCityName());
        claims.put(HOSPITAL_UNIT_ADDRESS_TOWNSHIP.getFieldName(),medic.getHospitalUnit().getAddress().getTownship());
        claims.put(HOSPITAL_UNIT_ADDRESS_POSTAL_CODE_ZIP.getFieldName(),medic.getHospitalUnit().getAddress().getPostalCodeZip());
        claims.put(HOSPITAL_UNIT_ADDRESS_STREET.getFieldName(),medic.getHospitalUnit().getAddress().getStreet());
        claims.put(HOSPITAL_UNIT_ADDRESS_NUMBER.getFieldName(),medic.getHospitalUnit().getAddress().getNumber());
        break;
      case ADMIN: // todo something
      case USER:
      default:
    }
    return claims;
  }

}
