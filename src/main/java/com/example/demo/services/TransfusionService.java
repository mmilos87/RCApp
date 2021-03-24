package com.example.demo.services;

import com.example.demo.entitety.AllCompletedTransfusions;
import com.example.demo.entitety.RcUserDonor;
import com.example.demo.entitety.RcUserMedic;
import com.example.demo.entitety.TransfusionQuery;
import com.example.demo.exception.JmbgIsNotValidException;
import com.example.demo.models.RequestTransfusionQuery;

public interface TransfusionService  {
  TransfusionQuery createOrUpdateTransfusionQuery(
      RequestTransfusionQuery query, RcUserMedic rcUserMedic)
      throws JmbgIsNotValidException;
  AllCompletedTransfusions transfusionCompleted(TransfusionQuery query, RcUserMedic medic, RcUserDonor donor);

}
