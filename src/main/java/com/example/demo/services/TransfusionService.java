package com.example.demo.services;

import com.example.demo.entitety.*;
import com.example.demo.helpers.enums.TransfusionTypes;

import java.util.List;

public interface TransfusionService {

  TransfusionQuery createOrUpdateTransfusionQuery(
      AppUser recipient, TransfusionTypes type, Long units, RcUserMedic medic);

  RcTransfusion nonDedicatedTransfusionCompleted(
      RcUserMedic medic, RcUserDonor donor, TransfusionTypes types);

  DedicatedTransfusions dedicatedTransfusionCompleted(
      Long transfusionQueryId, RcUserMedic medic, RcUserDonor donor);

  RejectedTransfusions rejectedTransfusionsSave(
      TransfusionTypes type, RcUserMedic medic, RcUserDonor donor, String note);

  List<TransfusionQuery> getAllQueryOfHospitalUnitByType(
      HospitalUnit hospitalUnit, TransfusionTypes types);
}
