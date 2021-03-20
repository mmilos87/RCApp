package com.example.demo.services;


import com.example.demo.entitety.AppUser;
import com.example.demo.entitety.HospitalUnit;
import com.example.demo.entitety.RcUserDonor;
import com.example.demo.entitety.RcUserMedic;
import com.example.demo.entitety.RcUserRecipient;
import com.example.demo.exception.EmailIsNotValidException;
import com.example.demo.exception.JmbgIsNotValidException;
import com.example.demo.exception.RegistrationException;
import com.example.demo.exception.RegistrationExceptionToken;
import com.example.demo.helpers.classes.UniqueMasterCitizenNumber;
import com.example.demo.helpers.enums.AppUserRole;
import com.example.demo.helpers.enums.AppMessages;
import com.example.demo.models.RegistrationRequestAppUser;
import com.example.demo.entitety.ConfirmationToken;
import com.example.demo.models.RegistrationRequestDonor;
import com.example.demo.models.RegistrationRequestHospitalUnit;
import com.example.demo.models.RegistrationRequestMedic;
import com.example.demo.models.RegistrationRequestRecipient;
import java.time.LocalDateTime;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

  private final AppUserService appUserService;
  private final RcDonorService rcDonorService;
  private final EmailValidatorService emailValidator;
  private final ConfirmationTokenService confirmationTokenService;
  private final HospitalUnitService hospitalUnitService;
  private final RCRecipientService rcRecipientService;
  private final RcUserMedicService rcUserMedicService;


  public String registerAppUser(RegistrationRequestAppUser request)
      throws EmailIsNotValidException, JmbgIsNotValidException {
    try {   emailValidator.test(request.getEmail());
        AppUser appUser=AppUser.builder()
            .gender(new UniqueMasterCitizenNumber(request.getJmbg().toString()).getGender())
            .firstName(request.getFirstName())
            .bloodType(request.getBloodType())
            .password(request.getPassword())
            .lastName(request.getLastName())
            .appUserRole(AppUserRole.USER)
            .email(request.getEmail())
            .jmbg(request.getJmbg())
            .build();
      String token = appUserService.singUp(appUser);
      confirmationTokenService.sendConfirmationEmail(request.getEmail(),request.getFirstName(),token);
      return token;
    } catch (EmailIsNotValidException e) {
     throw new EmailIsNotValidException(e.getAppMessages());
    }catch (JmbgIsNotValidException e) {
      throw new JmbgIsNotValidException(e.getAppMessages());
    }
  }

  @Override
  public String registerDonor(RegistrationRequestDonor request)
      throws EmailIsNotValidException, JmbgIsNotValidException, RegistrationException {
    String msg= registerAppUserIfnDoNotExist(request.getRequestAppUser());
    AppUser appUser = (AppUser) appUserService.loadUserByUsername(request.getRequestAppUser().getEmail());
    rcDonorService.addDonor(RcUserDonor.builder()
            .numberOfPlateletsGiving(request.getNumberOfPlateletsGiving())
            .numberOfBloodGiving(request.getNumberOfBloodGiving())
            .appUser(appUser)
            .build());
    return msg;
  }

  @Override
  public String registerRecipient(RegistrationRequestRecipient request)
      throws EmailIsNotValidException, JmbgIsNotValidException, RegistrationException {
    String msg= registerAppUserIfnDoNotExist(request.getRequestAppUser());
    AppUser appUser = (AppUser) appUserService.loadUserByUsername(request.getRequestAppUser().getEmail());
    HospitalUnit hospitalUnit= hospitalUnitService.getHosUnit(request.getRequestHospitalUnit()
                                                                     .getHospitalUnitName());
    RcUserRecipient recipient=RcUserRecipient.builder()
                                              .appUser(appUser)
                                              .hospitalUnit(hospitalUnit)
                                              .build();
    rcRecipientService.registerNewRecipient(recipient);
    return msg;
  }

  @Override
  public String registerMedic(RegistrationRequestMedic request)
      throws EmailIsNotValidException, JmbgIsNotValidException, RegistrationException {
      String msg =registerAppUserIfnDoNotExist(request.getRegistrationRequestAppUser());
    AppUser appUser = (AppUser) appUserService.loadUserByUsername(request.getRegistrationRequestAppUser().getEmail());
    HospitalUnit hospitalUnit= hospitalUnitService.getHosUnit(request.getRequestHospitalUnit()
                                                                     .getHospitalUnitName());
    RcUserMedic medic =RcUserMedic.builder()
                                  .hospitalUnit(hospitalUnit)
                                  .title(request.getTitle())
                                  .appUser(appUser)
                                  .build();
    rcUserMedicService.registerNewMedic(medic);
    return msg;
  }
  @Override
  public String registerHospitalUnit(RegistrationRequestHospitalUnit request) {
    return hospitalUnitService.registerNewHospitalUnit(HospitalUnit.builder()
                                                .hospitalUnitName(request.getHospitalUnitName())
                                                .address(request.getAddress())
                                                .build());

  }
  public String confirmToken(String token) throws RegistrationExceptionToken {
    ConfirmationToken confirmationToken = confirmationTokenService
        .getToken(token)
        .orElseThrow(
            () -> new RegistrationExceptionToken(AppMessages.TOKEN_NOT_FOUND));

    if (confirmationToken.getConfirmedAt() != null) {
      throw new RegistrationExceptionToken(AppMessages.TOKEN_ALREADY_CONFIRMED);
    }
    LocalDateTime expireAt = confirmationToken.getExpiresAt();
    if (expireAt.isBefore(LocalDateTime.now())) {
      throw new RegistrationExceptionToken(AppMessages.TOKEN_EXPIRED);
    }
    confirmationTokenService.setConfirmedAt(token);
    appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());
    return "confirmed";
  }
private String registerAppUserIfnDoNotExist(RegistrationRequestAppUser request) throws JmbgIsNotValidException,
        EmailIsNotValidException {
    return  appUserService.isExist(request.getAppUser())?"successful registration":registerAppUser(request);
  }
}



