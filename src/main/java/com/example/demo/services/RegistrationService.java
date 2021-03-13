package com.example.demo.services;

import com.example.demo.exception.EmailIsNotValidException;
import com.example.demo.exception.JmbgIsNotValidException;
import com.example.demo.exception.RegistrationException;
import com.example.demo.exception.RegistrationExceptionToken;
import com.example.demo.models.RegistrationRequestAppUser;
import com.example.demo.models.RegistrationRequestDonor;
import com.example.demo.models.RegistrationRequestHospitalUnit;
import com.example.demo.models.RegistrationRequestMedic;
import com.example.demo.models.RegistrationRequestRecipient;
import javax.transaction.Transactional;

public interface RegistrationService {

  String registerAppUser(RegistrationRequestAppUser request)
      throws EmailIsNotValidException, JmbgIsNotValidException;

  String registerDonor(RegistrationRequestDonor request)
      throws EmailIsNotValidException, JmbgIsNotValidException, RegistrationException;

  String registerRecipient(RegistrationRequestRecipient request)
      throws EmailIsNotValidException, JmbgIsNotValidException, RegistrationException;

  String registerMedic(RegistrationRequestMedic request)
      throws EmailIsNotValidException, JmbgIsNotValidException, RegistrationException;

  String registerHospitalUnit(RegistrationRequestHospitalUnit request)
      throws EmailIsNotValidException, JmbgIsNotValidException;


  @Transactional
  String confirmToken(String token) throws RegistrationExceptionToken;

}
