package com.example.demo.jwt;

import static com.example.demo.helpers.enums.JwtTokenFields.APP_USER_ROLE;
import static com.example.demo.helpers.enums.JwtTokenFields.BLOOD_TYPE;
import static com.example.demo.helpers.enums.JwtTokenFields.EMAIL;
import static com.example.demo.helpers.enums.JwtTokenFields.FIRST_NAME;
import static com.example.demo.helpers.enums.JwtTokenFields.GENDER_TYPE;
import static com.example.demo.helpers.enums.JwtTokenFields.HOSPITAL_UNIT_ADDRESS;
import static com.example.demo.helpers.enums.JwtTokenFields.HOSPITAL_UNIT_ID;
import static com.example.demo.helpers.enums.JwtTokenFields.HOSPITAL_UNIT_NAME;
import static com.example.demo.helpers.enums.JwtTokenFields.IS_BlOOD_CHECKED;
import static com.example.demo.helpers.enums.JwtTokenFields.IS_ENABLED;
import static com.example.demo.helpers.enums.JwtTokenFields.IS_LOCKED;
import static com.example.demo.helpers.enums.JwtTokenFields.JMBG;
import static com.example.demo.helpers.enums.JwtTokenFields.LAST_NAME;
import static com.example.demo.helpers.enums.JwtTokenFields.MEDIC_ID;
import static com.example.demo.helpers.enums.JwtTokenFields.MEDIC_TITLE;
import static com.example.demo.helpers.enums.JwtTokenFields.PASSWORD;

import com.example.demo.entitety.AppUser;
import com.example.demo.entitety.HospitalUnit;
import com.example.demo.entitety.RcUserMedic;
import com.example.demo.helpers.enums.AppUserRole;
import com.example.demo.helpers.enums.BloodTypes;
import com.example.demo.helpers.enums.GenderType;
import com.example.demo.helpers.enums.MedicTitle;
import com.example.demo.repos.RcUserMedicRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class JwtTokenHelper {

  private final JwtConfig jwtConfig;
  private final SecretKey secretKey;
  private final RcUserMedicRepository medicRepository;

  public String generateToken(Authentication authentication) {
    AppUser appUser = (AppUser) authentication.getPrincipal();
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
        claims.put(HOSPITAL_UNIT_ADDRESS.getFieldName(),medic.getHospitalUnit().getAddress());
        break;
      case ADMIN: // todo something
      case USER:
      default:
    }
    final java.sql.Date exp = java.sql.Date.valueOf(LocalDate.now()
        .plus(jwtConfig.getTokenExpirationAfterMinutes(), ChronoUnit.MINUTES));
    String token = Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(new Date())
        .setExpiration(exp)
        .signWith(secretKey)
        .compact();
    return token;
  }

  public String refreshToken(Claims claims){
    String token = Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(new Date())
        .setExpiration(java.sql.Date.valueOf(LocalDate.now()
            .plus(jwtConfig.getTokenExpirationAfterMinutes(),ChronoUnit.MINUTES)))
        .signWith(secretKey)
        .compact();
    return token;
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
    HospitalUnit hospitalUnit = HospitalUnit.builder()
        .id(body.get(HOSPITAL_UNIT_ID.getFieldName(), Long.class))
        .hospitalUnitName(body.get(HOSPITAL_UNIT_NAME.getFieldName(), String.class))
        .address(body.get(HOSPITAL_UNIT_ADDRESS.getFieldName(), String.class))
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

}
