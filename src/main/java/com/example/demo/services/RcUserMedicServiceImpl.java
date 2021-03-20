package com.example.demo.services;

import com.example.demo.entitety.RcUserMedic;
import com.example.demo.entitety.TransfusionQuery;
import com.example.demo.exception.RegistrationException;
import com.example.demo.helpers.enums.AppMessages;
import com.example.demo.jwt.JwtConfig;
import com.example.demo.jwt.JwtTokenHelper;
import com.example.demo.models.RequestTransfusionQuery;
import com.example.demo.repos.RcUserMedicRepository;
import com.example.demo.repos.TransfusionQueryRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RcUserMedicServiceImpl implements RcUserMedicService{

  private RcUserMedicRepository medicRepository;
  private TransfusionQueryRepository  transfusionQueryRepository;
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
  public TransfusionQuery createTransfusionQuery(RequestTransfusionQuery queryRequest, RcUserMedic medic){

//todo: logic
  
    return transfusionQueryRepository.save(new TransfusionQuery());


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
