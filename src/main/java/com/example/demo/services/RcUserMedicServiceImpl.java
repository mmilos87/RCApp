package com.example.demo.services;

import com.example.demo.entitety.RcUserMedic;
import com.example.demo.entitety.TransfusionQuery;
import com.example.demo.exception.RegistrationException;
import com.example.demo.helpers.enums.AppMessages;
import com.example.demo.models.RequestTransfusionQuery;
import com.example.demo.repos.HospitalUnitRepository;
import com.example.demo.repos.RcUserMedicRepository;
import com.example.demo.repos.TransfusionQueryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RcUserMedicServiceImpl implements RcUserMedicService{

  private RcUserMedicRepository medicRepository;
  private AppUserService appUserService;
  private HospitalUnitRepository hospitalUnitRepository;
  private TransfusionQueryRepository  transfusionQueryRepository;

  @Override
  public void registerNewMedic(RcUserMedic rcUserMedic)
      throws RegistrationException  {

    boolean isMedicExist = medicRepository.findByAppUser(rcUserMedic.getAppUser()).isPresent();
    if (!isMedicExist) {
      medicRepository.save(rcUserMedic);
    } else {
      throw new RegistrationException(AppMessages.USER_ALREADY_REGISTERED);
    }
  }

  public TransfusionQuery createTransfusionQuery(RequestTransfusionQuery queryRequest, RcUserMedic medic){

//todo: logic
  
    return transfusionQueryRepository.save(new TransfusionQuery());


  }


}
