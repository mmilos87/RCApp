package com.example.demo.services;

public interface LoginAttemptService {
    void failureLoginAttempt(String username);
    void successLoginAttempt(String username);
}
