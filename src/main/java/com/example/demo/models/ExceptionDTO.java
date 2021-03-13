package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExceptionDTO {

  private final String errorMessage;

  public ExceptionDTO(@JsonProperty("errorMsg") String errorMessage) {

    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
