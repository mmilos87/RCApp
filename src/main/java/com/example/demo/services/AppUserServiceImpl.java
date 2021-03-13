package com.example.demo.services;

import com.example.demo.entitety.AppUser;
import com.example.demo.repos.AppUserRepository;
import com.example.demo.exception.EmailIsNotValidException;
import com.example.demo.helpers.enums.AppMessages;
import com.example.demo.entitety.ConfirmationToken;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService {

  private final AppUserRepository appUserRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final ConfirmationTokenService confirmationTokenService;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return appUserRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException(
            String.format(AppMessages.USER_WITH_THAT_EMAIL_NOT_FOUND.getMessage(), email)));
  }

  public String singUp(AppUser appUser) throws EmailIsNotValidException {
    if (isExist(appUser)) {
      throw new EmailIsNotValidException(AppMessages.EMAIL_IS_ALREADY_TAKEN);
    }
    String encodePassword = bCryptPasswordEncoder.encode(appUser.getPassword());
    appUser.setPassword(encodePassword);
    appUserRepository.save(appUser);
    String token = UUID.randomUUID().toString();
    confirmationTokenService.saveConfirmationToken(ConfirmationToken.builder()
        .expiresAt(LocalDateTime.now().plusMinutes(15))
        .createdAt(LocalDateTime.now())
        .appUser(appUser)
        .token(token)
        .build());
    return token;
  }

  public void enableAppUser(String email) {
    appUserRepository.enableAppUser(email);
  }

  @Override
  public boolean isExist(AppUser appUser) {
   return appUserRepository.findByEmail(appUser.getEmail()).isPresent();
  }

}
