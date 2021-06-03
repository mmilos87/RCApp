package com.example.demo.services;

import com.example.demo.entitety.AppUser;
import com.example.demo.entitety.LoginAttempts;
import com.example.demo.repos.AppUserRepository;
import com.example.demo.repos.LoginAttemptRepo;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Log4j2
public class LoginAttemptServiceImpl implements LoginAttemptService {

  private final AppUserRepository userRepository;
  private final LoginAttemptRepo attemptsRepo;
  private static final int MAX_FAILURE_ATTEMPTS = 3;

  @Override
  public void failureLoginAttempt(String username) {
    try {
        AppUser appUser = userRepository
                .findByEmail(username)
                .orElseThrow(()->new IllegalStateException("usr not found"));
      // TODO exception handling
      if (!appUser.isEnabled()) throw new IllegalStateException("Account is not enabled");
      attemptsRepo
          .findByAppUser(appUser)
          .ifPresentOrElse(
              loginAttempts -> {
                if (loginAttempts.getNumberOfFailureAttempts() < MAX_FAILURE_ATTEMPTS)
                  loginAttempts.increaseNumberOfFailureAttempts();
                if (loginAttempts.getNumberOfFailureAttempts() == MAX_FAILURE_ATTEMPTS)
                 //todo disable user or send notification email
                loginAttempts.setDateOfLastAttempt(LocalDateTime.now());
                attemptsRepo.save(loginAttempts);
              },
              () ->
                  attemptsRepo.save(
                      LoginAttempts.builder()
                          .appUser(appUser)
                          .numberOfFailureAttempts(1)
                          .dateOfLastAttempt(LocalDateTime.now())
                          .build()));
    } catch (UsernameNotFoundException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  @Override
  public void successLoginAttempt(String username) {
      AppUser appUser = userRepository
              .findByEmail(username)
              .orElseThrow(()->new IllegalStateException("usr not found"));
    attemptsRepo
        .findByAppUser(appUser)
        .ifPresentOrElse(
            loginAttempts -> {
              loginAttempts.resetNumberOfFailureAttempts();
              attemptsRepo.save(loginAttempts);
            },
            () ->
                attemptsRepo.save(
                    LoginAttempts.builder()
                        .appUser(appUser)
                        .dateOfLastAttempt(LocalDateTime.now())
                        .build()));
  }
}
