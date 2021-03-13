package com.example.demo.exception;

import com.example.demo.helpers.enums.AppMessages;

public class JmbgIsNotValidException extends Exception {
  private final AppMessages appMessages;

  public JmbgIsNotValidException(AppMessages appMessages) {
    super(appMessages.getMessage());
    this.appMessages = appMessages;
  }

  public AppMessages getAppMessages() {
    return appMessages;
  }

}
