package com.example.demo.models;
import com.example.demo.entitety.UserCity;
import lombok.Getter;

@Getter
public class RegistrationRcAddress {
    private UserCity city;
    private String township;
    private Long postalCodeZip;
    private String street;
    private String number;


}
