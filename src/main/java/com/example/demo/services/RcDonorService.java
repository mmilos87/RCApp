package com.example.demo.services;

import com.example.demo.entitety.AllCompletedTransfusions;
import com.example.demo.entitety.RcUserDonor;
import com.example.demo.exception.EmailIsNotValidException;
import com.example.demo.exception.RegistrationException;

public interface RcDonorService {
 void addDonor(RcUserDonor donor) throws EmailIsNotValidException, RegistrationException;
 RcUserDonor successfulGiving(AllCompletedTransfusions completedTransfusions);
}
