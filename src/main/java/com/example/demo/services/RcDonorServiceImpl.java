package com.example.demo.services;

import com.example.demo.entitety.AllCompletedTransfusions;
import com.example.demo.entitety.RcUserDonor;
import com.example.demo.exception.RegistrationException;
import com.example.demo.helpers.enums.AppMessages;
import com.example.demo.repos.RcUserDonorsRepository;
import java.time.LocalDateTime;
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

  @Override
  public RcUserDonor successfulGiving(AllCompletedTransfusions completedTransfusions){
    RcUserDonor donor=completedTransfusions.getDonor();
    switch (completedTransfusions.getType()){
      case BLOOD:
        donor.setDateLastBloodGiving(LocalDateTime.now());
        donor.setNumberOfBloodGiving(donor.getNumberOfBloodGiving()+1);
        break;
      case PLATELETS:
        donor.setDateLastPlateletsGiving(LocalDateTime.now());
        donor.setNumberOfPlateletsGiving(donor.getNumberOfPlateletsGiving()+1);
        break;
      case BLOOD_PLASMA:
        donor.setDateLastBloodPlasmaGiving(LocalDateTime.now());
        donor.setNumberOfBloodPlasmaGiving(donor.getNumberOfBloodPlasmaGiving()+1);
    }
    return donorsRepository.save(donor);
  }

}
