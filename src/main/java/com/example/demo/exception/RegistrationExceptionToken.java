package com.example.demo.exception;

import com.example.demo.helpers.enums.AppMessages;

public class RegistrationExceptionToken extends RegistrationException{

  public RegistrationExceptionToken( AppMessages appMessages) {
    super(appMessages);
  }

}
