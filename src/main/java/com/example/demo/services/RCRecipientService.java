package com.example.demo.services;

import com.example.demo.entitety.RcUserRecipient;
import com.example.demo.exception.EmailIsNotValidException;
import com.example.demo.exception.RegistrationException;

public interface RCRecipientService  {
     void registerNewRecipient(RcUserRecipient recipient)
          throws EmailIsNotValidException, RegistrationException;
}
