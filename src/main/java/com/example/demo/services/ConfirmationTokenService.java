package com.example.demo.services;

import com.example.demo.entitety.ConfirmationToken;
import java.util.Optional;

public interface ConfirmationTokenService {

  void saveConfirmationToken(ConfirmationToken confirmationToken);

  Optional<ConfirmationToken> getToken(String token);

  void setConfirmedAt(String token);

  void sendConfirmationEmail(String email,String name,String token);

}
