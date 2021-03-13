package com.example.demo.services;

import com.example.demo.models.EmailMessage;

public interface EmailService {
  void send(EmailMessage emailMessage);

}
