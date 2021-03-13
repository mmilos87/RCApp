package com.example.demo.services;

import com.example.demo.entitety.RcUserRecipient;
import com.example.demo.exception.EmailIsNotValidException;
import com.example.demo.exception.RegistrationException;
import com.example.demo.helpers.enums.AppMessages;
import com.example.demo.repos.HospitalUnitRepository;
import com.example.demo.repos.RcUserRecipientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RCRecipientServiceImpl implements RCRecipientService{

  private RcUserRecipientRepository recipientRepository;
  private AppUserService appUserService;

  @Override
  public void registerNewRecipient(RcUserRecipient recipient)
      throws  RegistrationException {
    if (!recipientRepository.findByAppUser(recipient.getAppUser()).isPresent()) {
      recipientRepository.save(recipient);
    } else {
      throw new RegistrationException(AppMessages.USER_ALREADY_REGISTERED);
    }
  }
}
