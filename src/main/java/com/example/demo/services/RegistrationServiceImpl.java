package com.example.demo.services;


import com.example.demo.entitety.*;
import com.example.demo.exception.EmailIsNotValidException;
import com.example.demo.exception.JmbgIsNotValidException;
import com.example.demo.exception.RegistrationException;
import com.example.demo.exception.RegistrationExceptionToken;
import com.example.demo.helpers.classes.UniqueMasterCitizenNumberHelper;
import com.example.demo.helpers.enums.AppUserRole;
import com.example.demo.helpers.enums.AppMessages;
import com.example.demo.models.*;

import java.time.LocalDateTime;

import com.example.demo.repos.RCAddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

  private final AppUserService appUserService;
  private final RcDonorService rcDonorService;
  private final EmailService emailService;
  private final ConfirmationTokenService confirmationTokenService;
  private final HospitalUnitService hospitalUnitService;
  private final RcUserMedicService rcUserMedicService;
  private final RCAddressRepository rcAddressRepository;

  public String registerAppUser(RegistrationRequestAppUser request)
      throws EmailIsNotValidException, JmbgIsNotValidException {
    try {   emailService.testEmailAddress(request.getEmail());
        AppUser appUser=AppUser.builder()
            .gender(new UniqueMasterCitizenNumberHelper(request.getJmbg().toString()).getGender())
            .firstName(request.getFirstName())
            .bloodType(request.getBloodType())
            .password(request.getPassword())
            .lastName(request.getLastName())
            .appUserRole(AppUserRole.USER_MEDIC)
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
    RcAddress address=saveAddress(request.getRegistrationRcAddress());
    AppUser appUser = (AppUser) appUserService.loadUserByUsername(request.getRequestAppUser().getEmail());
    rcDonorService.addDonor(RcUserDonor.builder()
            .numberOfPlateletsGiving(request.getNumberOfPlateletsGiving())
            .numberOfBloodGiving(request.getNumberOfBloodGiving())
            .address(address)
            .appUser(appUser)
            .build());
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
    RcAddress address = saveAddress(request.getRegistrationRcAddress());
    return hospitalUnitService.registerNewHospitalUnit(HospitalUnit.builder()
                                                .hospitalUnitName(request.getHospitalUnitName())
                                                .address(address)
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
  private RcAddress saveAddress(RegistrationRcAddress registrationRcAddress){
    UserCity userCity =
        UserCity.builder()
            .id(registrationRcAddress.getCity().getId())
            .cityName(registrationRcAddress.getCity().getCityName())
            .build();
    return  rcAddressRepository.save(
            RcAddress.builder()
                    .city(userCity)
                    .street(registrationRcAddress.getStreet())
                    .number(registrationRcAddress.getNumber())
                    .township(registrationRcAddress.getTownship())
                    .postalCodeZip(registrationRcAddress.getPostalCodeZip())
                    .build());
  }
private String registerAppUserIfnDoNotExist(RegistrationRequestAppUser request)
    throws JmbgIsNotValidException, EmailIsNotValidException {
    return  appUserService.isExist(request.getAppUser())?"successful registration":registerAppUser(request);
  }
}



