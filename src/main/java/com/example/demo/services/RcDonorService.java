package com.example.demo.services;

import com.example.demo.entitety.RcTransfusion;
import com.example.demo.entitety.RcUserDonor;
import com.example.demo.exception.EmailIsNotValidException;
import com.example.demo.exception.JmbgIsNotValidException;
import com.example.demo.exception.RegistrationException;
import com.example.demo.helpers.enums.TransfusionTypes;
import com.example.demo.models.RegistrationRequestAppUser;
import com.example.demo.repos.RcUserDonorsRepository;

public interface RcDonorService {
 void addDonor(RcUserDonor donor) throws EmailIsNotValidException, RegistrationException;
 RcUserDonor successfulTransfusion(RcTransfusion completedTransfusions);
 RcUserDonor rejectedTransfusion(RcUserDonor donor);
 RcUserDonor getOrCreateDonor(RegistrationRequestAppUser donor) throws JmbgIsNotValidException;

}
