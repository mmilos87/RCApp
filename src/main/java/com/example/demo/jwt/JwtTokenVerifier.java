package com.example.demo.jwt;

import com.example.demo.entitety.AppUser;
import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class JwtTokenVerifier extends OncePerRequestFilter {

  private final JwtConfig jwtConfig;
  private final SecretKey secretKey;
  private final JwtTokenHelper jwtTokenHelper;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String authHeader = request.getHeader(jwtConfig.getAuthorizationHeaders());
    if (Strings.isNullOrEmpty(authHeader) || !authHeader.startsWith(jwtConfig.getTokenPrefix())) {
      filterChain.doFilter(request, response);
      return;
    }
    try {
      String token = authHeader.replace(jwtConfig.getTokenPrefix(), "");
      Claims body = Jwts.parserBuilder()
          .setSigningKey(secretKey)
          .build()
          .parseClaimsJws(token)
          .getBody();
      AppUser appUser = jwtTokenHelper.extractAppUserFromJwtClaims(body);
      Authentication authentication = new UsernamePasswordAuthenticationToken(
          appUser.getEmail(),
          null,
          appUser.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      token=jwtTokenHelper.refreshToken(body);
      response.addHeader(jwtConfig.getAuthorizationHeaders(), jwtConfig.getTokenPrefix() + token);
    } catch (JwtException e) {
      throw new IllegalStateException("token is not valid");

    }

    filterChain.doFilter(request, response);

  }
}
