package com.example.demo.services;

import com.example.demo.exception.EmailIsNotValidException;

public interface EmailValidatorService {
   void test(String email) throws EmailIsNotValidException;
}
