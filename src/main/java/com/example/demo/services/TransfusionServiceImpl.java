package com.example.demo.services;

import com.example.demo.entitety.*;
import com.example.demo.exception.JmbgIsNotValidException;
import com.example.demo.helpers.enums.TransfusionTypes;
import com.example.demo.models.RequestTransfusionQuery;
import com.example.demo.repos.DedicatedTransfusionRepository;
import com.example.demo.repos.RcTransfusionRepository;
import com.example.demo.repos.RejectedTransfusionsRepository;
import com.example.demo.repos.TransfusionQueryRepository;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransfusionServiceImpl implements TransfusionService {

  private final TransfusionQueryRepository transfusionQueryRepository;
  private final RcTransfusionRepository rcTransfusionRepository;
  private final AppUserService appUserService;
  private final RcDonorService rcDonorService;
  private final DedicatedTransfusionRepository dedicatedTransfusionRepository;
  private final RejectedTransfusionsRepository rejectedTransfusionsRepository;

  @Override
  public TransfusionQuery createOrUpdateTransfusionQuery(
          AppUser recipient, TransfusionTypes type, Long units,
          RcUserMedic medic) {
    TransfusionQuery transfusionQuery =
        TransfusionQuery.builder()
            .transfusionType(type)
            .requiredUnits(units)
            .hospitalUnit(medic.getHospitalUnit())
            .createdAt(LocalDateTime.now())
            .recipient(recipient)
            .rcUserMedic(medic)
            .build();
    transfusionQueryRepository
        .findByRecipientBYType(recipient, type)
        .ifPresentOrElse(
            query -> {
              query.setRequiredUnits(units);
              transfusionQueryRepository.save(query);
            },
            () -> transfusionQueryRepository.save(transfusionQuery));
    return transfusionQuery;
  }

  @Override
  public RcTransfusion nonDedicatedTransfusionCompleted(
      RcUserMedic medic, RcUserDonor donor, TransfusionTypes types) {
    RcTransfusion rcTransfusion =
        transfusionSave(medic.getHospitalUnit(), types, medic, donor, false);
    return rcTransfusion;
  }

  @Override
  public DedicatedTransfusions dedicatedTransfusionCompleted(
      Long transfusionQueryId, RcUserMedic medic, RcUserDonor donor) {
    TransfusionQuery query=transfusionQueryRepository.findById(transfusionQueryId).get();
    //todo izuzetak...
    RcTransfusion transfusion =
        transfusionSave(query.getHospitalUnit(), query.getTransfusionType(), medic, donor, true);
    query.setRequiredUnits(query.getRequiredUnits() - 1);
    if (query.getRequiredUnits() > 0) {
      transfusionQueryRepository.save(query);
    } else {
      transfusionQueryRepository.delete(query);
    }
    DedicatedTransfusions dedicatedTransfusions = dedicatedTransfusionRepository.save(
            DedicatedTransfusions.builder()
                .transfusion(transfusion)
                .recipient(query.getRecipient())
                .build());
    return dedicatedTransfusions;
  }

  private RcTransfusion transfusionSave(
          HospitalUnit hospitalUnit, TransfusionTypes types,RcUserMedic medic, RcUserDonor donor, Boolean isDedicated) {
    RcTransfusion transfusion = rcTransfusionRepository.save(
            RcTransfusion.builder()
                    .hospitalUnit(hospitalUnit)
                    .type(types)
                    .isDedicated(isDedicated)
                    .date(LocalDateTime.now())
                    .rcUserMedic(medic)
                    .donor(donor)
                    .build());
    rcDonorService.successfulTransfusion(transfusion);
    return transfusion;
  }

  @Override
  public RejectedTransfusions rejectedTransfusionsSave(
      TransfusionTypes type, RcUserMedic medic, RcUserDonor donor, String note) {
    RcUserDonor userDonor = rcDonorService.rejectedTransfusion(donor);
    switch (type) {
      case BLOOD:
        type = TransfusionTypes.BLOOD_REJECTED;
        break;
      case PLATELETS:
        type = TransfusionTypes.PLATELETS_REJECTED;
        break;
      case BLOOD_PLASMA:
        type = TransfusionTypes.BLOOD_PLASMA_REJECTED;
        break;
      default:
        break;
    }
    RcTransfusion transfusion = rcTransfusionRepository.save(
            RcTransfusion.builder()
                .hospitalUnit(medic.getHospitalUnit())
                .type(type)
                .date(LocalDateTime.now())
                .rcUserMedic(medic)
                .donor(userDonor)
                .build());
    RejectedTransfusions rejectedTransfusions = rejectedTransfusionsRepository.save(
            RejectedTransfusions.builder()
                    .transfusion(transfusion)
                    .note(note)
                    .build());
    return rejectedTransfusions;
  }
}
