package com.example.demo.services;

import com.example.demo.entitety.AppUser;
import com.example.demo.exception.JmbgIsNotValidException;
import com.example.demo.jwt.JwtConfig;
import com.example.demo.jwt.JwtTokenHelper;
import com.example.demo.models.RegistrationRequestAppUser;
import com.example.demo.repos.AppUserRepository;
import com.example.demo.exception.EmailIsNotValidException;
import com.example.demo.helpers.enums.AppMessages;
import com.example.demo.entitety.ConfirmationToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
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
  private final JwtConfig jwtConfig;
  private final JwtTokenHelper jwtTokenHelper;
  private final SecretKey secretKey;

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

  public AppUser extractAppUserFromRequest(HttpServletRequest request) {
    String authHeader = request.getHeader(jwtConfig.getAuthorizationHeaders());
    String token = authHeader.replace(jwtConfig.getTokenPrefix(), "");
    Claims body = Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody();
    return jwtTokenHelper.extractAppUserFromJwtClaims(body);
  }

  public AppUser getOrCreateAppUser(RegistrationRequestAppUser registrationRequestAppUser)
      throws JmbgIsNotValidException {
    AppUser appUser=appUserRepository.existsById(registrationRequestAppUser.getJmbg())?
        appUserRepository.findById(registrationRequestAppUser.getJmbg()).get():
        appUserRepository.save(registrationRequestAppUser.getAppUser());
  return appUser;
  }

}
