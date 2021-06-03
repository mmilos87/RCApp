package com.example.demo.security.config;

import com.example.demo.entitety.AppUser;
import com.example.demo.helpers.classes.OAuth2GoogleUserInfo;
import com.example.demo.helpers.enums.RCLoginAuthProvider;
import com.example.demo.jwt.JwtConfig;
import com.example.demo.jwt.JwtTokenHelper;
import com.example.demo.repos.AppUserRepository;
import com.google.common.net.HttpHeaders;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
@Component
public class RcOAuth2SuccessHandler implements AuthenticationSuccessHandler {
  private final AppUserRepository userRepository;
  private final JwtTokenHelper jwtHelper;
  private final JwtConfig jwtConfig;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                                  Authentication authentication) throws IOException, ServletException {
      Map<String, Object> attributes = ((DefaultOidcUser) authentication.getPrincipal()).getAttributes();
      OAuth2GoogleUserInfo userGoogle = new OAuth2GoogleUserInfo(attributes);
      String email = userGoogle.getEmail();
      userRepository
        .findByEmail(email)
        .ifPresentOrElse(
            user -> {
              if (user.getAuthProvider().equals(RCLoginAuthProvider.LOCAL)
                  && !user.getAuthProvider().equals(RCLoginAuthProvider.GOOGLE)) {
                user.setAuthProvider(RCLoginAuthProvider.GOOGLE_AND_LOCAL);
                user = userRepository.save(user);
              }
              addTokenToHeader(user, httpServletResponse);
            },
            () -> {
              /*
              todo:
                  endpoint koji ce da promeni jmbg, ako je gender not chek prvo da promeni jmbg
                  posle priajvljivanja, tj registracije...
            */
              AppUser user = userRepository.save(userGoogle.gelLeagueUser());
              addTokenToHeader(user, httpServletResponse);
            });
  }

  private void addTokenToHeader(AppUser user, HttpServletResponse httpServletResponse) {
    String token = jwtHelper.generateToken(user);
    httpServletResponse.addHeader(HttpHeaders.AUTHORIZATION, jwtConfig.getTokenPrefix() + token);
  }
}
