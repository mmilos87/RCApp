package com.example.demo.services;

import com.example.demo.entitety.AllCompletedTransfusions;
import com.example.demo.entitety.AppUser;
import com.example.demo.entitety.RcUserDonor;
import com.example.demo.entitety.RcUserMedic;
import com.example.demo.entitety.TransfusionQuery;
import com.example.demo.exception.JmbgIsNotValidException;
import com.example.demo.models.RequestTransfusionQuery;
import com.example.demo.repos.AllCompletedTransfusionsRepository;
import com.example.demo.repos.TransfusionQueryRepository;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransfusionServiceImpl implements TransfusionService {

  private final TransfusionQueryRepository transfusionQueryRepository;
  private final AllCompletedTransfusionsRepository completedTransfusionsRepository;
  private final AppUserService appUserService;
  private final RcDonorService rcDonorService;

  @Override
  public TransfusionQuery createOrUpdateTransfusionQuery(RequestTransfusionQuery queryRequest,
      RcUserMedic medic)
      throws JmbgIsNotValidException {
    AppUser recipient = appUserService.getOrCreateAppUser(queryRequest.getRecipient());
    TransfusionQuery transfusionQuery = TransfusionQuery.builder()
        .transfusionType(queryRequest.getTransfusionType())
        .requiredUnits(queryRequest.getRequiredUnits())
        .hospitalUnit(medic.getHospitalUnit())
        .createdAt(LocalDateTime.now())
        .recipient(recipient)
        .rcUserMedic(medic)
        .build();
    transfusionQueryRepository.findByRecipientBYType(recipient, queryRequest.getTransfusionType())
        .ifPresentOrElse(query -> {
          query.setRequiredUnits(queryRequest.getRequiredUnits());
          transfusionQueryRepository.save(query);
        }, () -> transfusionQueryRepository.save(transfusionQuery));
  return transfusionQuery;
  }

  @Override
  public AllCompletedTransfusions transfusionCompleted(TransfusionQuery query, RcUserMedic medic,
      RcUserDonor donor) {
    AllCompletedTransfusions completedTransfusions = completedTransfusionsRepository.save(
        AllCompletedTransfusions.builder()
            .hospitalUnit(query.getHospitalUnit())
            .type(query.getTransfusionType())
            .recipient(query.getRecipient())
            .date(LocalDateTime.now())
            .rcUserMedic(medic)
            .donor(donor)
            .build());
    query.setRequiredUnits(query.getRequiredUnits() - 1);
    if (query.getRequiredUnits() > 0) {
      transfusionQueryRepository.save(query);
    } else {
      transfusionQueryRepository.delete(query);
    }
    rcDonorService.successfulGiving(completedTransfusions);
    return completedTransfusions;
  }


}
