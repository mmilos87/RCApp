package com.example.demo.services;

import com.example.demo.entitety.HospitalUnit;
import com.example.demo.jwt.JwtConfig;
import com.example.demo.jwt.JwtTokenHelper;
import com.example.demo.repos.HospitalUnitRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HospitalUnitServiceImpl implements HospitalUnitService{

  private final HospitalUnitRepository hospitalUnitRepository;
  private final JwtTokenHelper jwtTokenHelper;
  private final JwtConfig jwtConfig;
  private final SecretKey  secretKey;

  @Override
  public String registerNewHospitalUnit(HospitalUnit hospitalUnit) {
    boolean isHuExist = hospitalUnitRepository
        .findByHospitalUnitName(hospitalUnit.getHospitalUnitName()).isPresent();
    if (!isHuExist) {
      hospitalUnitRepository.save(hospitalUnit);
      return "registered";
    }
    return "hospital Unit exist";

  }
  @Override
  public HospitalUnit extractAppUserFromRequest(HttpServletRequest request) {
    String authHeader = request.getHeader(jwtConfig.getAuthorizationHeaders());
    String token = authHeader.replace(jwtConfig.getTokenPrefix(), "");
    Claims body = Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody();
    return jwtTokenHelper.extractHospitalUnitFromJwtClaims(body);
  }

  public HospitalUnit getHosUnit(String name) {
   return hospitalUnitRepository.findByHospitalUnitName(name).get();
  }
}
