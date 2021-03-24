package com.example.demo.models;

import com.example.demo.helpers.enums.TransfusionTypes;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RequestTransfusionQuery {

    private RegistrationRequestAppUser recipient;
    private TransfusionTypes transfusionType;
    private Long requiredUnits;

}
