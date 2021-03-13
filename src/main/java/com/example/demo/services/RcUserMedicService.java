package com.example.demo.services;

import com.example.demo.entitety.RcUserMedic;
import com.example.demo.entitety.TransfusionQuery;
import com.example.demo.exception.EmailIsNotValidException;
import com.example.demo.exception.RegistrationException;
import com.example.demo.models.RequestTransfusionQuery;

public interface RcUserMedicService {
 void registerNewMedic(RcUserMedic rcUserMedic)
     throws RegistrationException, EmailIsNotValidException;
 TransfusionQuery createTransfusionQuery(RequestTransfusionQuery query, RcUserMedic rcUserMedic);
}
