package com.example.demo.services;

import com.example.demo.entitety.RcUserMedic;
import com.example.demo.exception.EmailIsNotValidException;
import com.example.demo.exception.RegistrationException;
import javax.servlet.http.HttpServletRequest;

public interface RcUserMedicService {
 void registerNewMedic(RcUserMedic rcUserMedic)
     throws RegistrationException, EmailIsNotValidException;
 RcUserMedic extractAppUserFromRequest(HttpServletRequest request);


}
