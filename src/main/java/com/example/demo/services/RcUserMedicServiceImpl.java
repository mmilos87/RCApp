package com.example.demo.services;

import com.example.demo.entitety.RcUserMedic;
import com.example.demo.exception.RegistrationException;
import com.example.demo.helpers.enums.AppMessages;
import com.example.demo.jwt.JwtConfig;
import com.example.demo.jwt.JwtTokenHelper;
import com.example.demo.repos.RcUserMedicRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RcUserMedicServiceImpl implements RcUserMedicService{

  private final RcUserMedicRepository medicRepository;
  private final JwtTokenHelper jwtTokenHelper;
  private final JwtConfig jwtConfig;
  private final SecretKey secretKey;

  @Override
  public void registerNewMedic(RcUserMedic rcUserMedic)
      throws RegistrationException  {

    boolean isMedicExist = medicRepository.findByAppUser(rcUserMedic.getAppUser()).isPresent();
    if (!isMedicExist) {
      medicRepository.save(rcUserMedic);
    } else {
      throw new RegistrationException(AppMessages.USER_ALREADY_REGISTERED);
    }
  }


  @Override
  public RcUserMedic extractAppUserFromRequest(HttpServletRequest request) {
    String authHeader = request.getHeader(jwtConfig.getAuthorizationHeaders());
    String token = authHeader.replace(jwtConfig.getTokenPrefix(), "");
    Claims body = Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody();
    return jwtTokenHelper.extractMedicFromJwtClaims(body);
  }
}
