package com.example.demo.services;

import com.example.demo.entitety.AppUser;
import com.example.demo.exception.EmailIsNotValidException;
import com.example.demo.exception.JmbgIsNotValidException;
import com.example.demo.models.RegistrationRequestAppUser;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {

  String singUp(AppUser appUser) throws EmailIsNotValidException;

  void enableAppUser(String email);
  boolean isExist(AppUser  appUser);
  AppUser extractAppUserFromRequest(HttpServletRequest request);
  AppUser getOrCreateAppUser(RegistrationRequestAppUser registrationRequestAppUser)
      throws JmbgIsNotValidException;
}
