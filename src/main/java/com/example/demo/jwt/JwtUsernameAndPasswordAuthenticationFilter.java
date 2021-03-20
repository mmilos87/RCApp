package com.example.demo.jwt;


import com.example.demo.models.UsernameAndPasswordAuthenticationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class JwtUsernameAndPasswordAuthenticationFilter extends
    UsernamePasswordAuthenticationFilter {

  private final JwtConfig jwtConfig;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenHelper jwtTokenHelper;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response)
      throws AuthenticationException {
    try {
      UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
          .readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);
      Authentication authenticate = new UsernamePasswordAuthenticationToken(
          authenticationRequest.getUsername(),
          authenticationRequest.getPassword());
      return authenticationManager.authenticate(authenticate);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, FilterChain chain, Authentication authResult) {
    String token = jwtTokenHelper.generateToken(authResult);
    response.addHeader(jwtConfig.getAuthorizationHeaders(), jwtConfig.getTokenPrefix() + token);

  }
}
