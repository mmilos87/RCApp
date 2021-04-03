package com.example.demo.services;

import com.example.demo.exception.EmailIsNotValidException;
import com.example.demo.models.EmailMessage;

public interface EmailService {
  void send(EmailMessage emailMessage);
  void testEmailAddress(String email) throws EmailIsNotValidException;
}
