package com.example.demo.models;

import lombok.Getter;

@Getter
public class DedicatedTransfusionRequest {
    private RegistrationRequestAppUser donor;
    private Long transfusionQueryId;
}
