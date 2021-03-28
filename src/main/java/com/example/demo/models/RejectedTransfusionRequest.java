package com.example.demo.models;

import com.example.demo.helpers.enums.TransfusionTypes;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RejectedTransfusionRequest {
    private RegistrationRequestAppUser donor;
    private String note;
    private TransfusionTypes type;

}
