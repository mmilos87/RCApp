package com.example.demo.exception;

import com.example.demo.helpers.enums.AppMessages;

public class EmailIsNotValidException extends Exception{
  private final AppMessages appMessages;

  public EmailIsNotValidException(AppMessages appMessages) {
    super(appMessages.getMessage());
    this.appMessages = appMessages;
  }

  public AppMessages getAppMessages() {
    return appMessages;
  }
}
