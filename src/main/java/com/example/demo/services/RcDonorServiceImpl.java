package com.example.demo.services;

import com.example.demo.entitety.RcUserDonor;
import com.example.demo.exception.EmailIsNotValidException;
import com.example.demo.exception.RegistrationException;
import com.example.demo.helpers.enums.AppMessages;
import com.example.demo.repos.RcUserDonorsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RcDonorServiceImpl implements RcDonorService{

  private RcUserDonorsRepository donorsRepository;
  private AppUserService appUserService;

  @Override
  public void addDonor(RcUserDonor donor) throws   RegistrationException {
    if (!donorsRepository.findByAppUser(donor.getAppUser()).isPresent()) {
      donorsRepository.saveAndFlush(donor);
    }else throw new RegistrationException(AppMessages.USER_ALREADY_REGISTERED);

  }
}
