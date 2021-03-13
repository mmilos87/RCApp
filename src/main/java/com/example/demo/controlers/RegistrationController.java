package com.example.demo.controlers;

import com.example.demo.exception.EmailIsNotValidException;
import com.example.demo.exception.JmbgIsNotValidException;
import com.example.demo.exception.RegistrationException;
import com.example.demo.exception.RegistrationExceptionToken;
import com.example.demo.models.RegistrationRequestAppUser;
import com.example.demo.models.RegistrationRequestDonor;
import com.example.demo.models.RegistrationRequestHospitalUnit;
import com.example.demo.models.RegistrationRequestMedic;
import com.example.demo.services.AppUserService;
import com.example.demo.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController extends MainController{

  private RegistrationService registrationService;
  private AppUserService appUserService;

  //registration new app user
  @PostMapping(value ="/1")
  public String registerNewAppUser(@RequestBody RegistrationRequestAppUser request)
      throws EmailIsNotValidException, JmbgIsNotValidException {
    return registrationService.registerAppUser(request);
  }


 //registration new donor
  @PostMapping(value ="/1/donor")
  public String registerAppUserDonor(@RequestBody RegistrationRequestDonor request)
      throws EmailIsNotValidException, JmbgIsNotValidException, RegistrationException {
      return registrationService.registerDonor(request);

  }
  //registration new hospital unit
  @PostMapping(value ="/1/hospital")
  public String registerHospitalUnit(@RequestBody RegistrationRequestHospitalUnit request)
      throws EmailIsNotValidException, JmbgIsNotValidException {
    return registrationService.registerHospitalUnit(request);

  }


  //registration new medic
  @PostMapping(value ="/1/medic")
  public String registerAppUserMedic(@RequestBody RegistrationRequestMedic request)
      throws EmailIsNotValidException, JmbgIsNotValidException, RegistrationException {
    return registrationService.registerMedic(request);

  }
  @GetMapping(path = "confirm")
  public String confirmNewAppUser(@RequestParam("token")String token)
      throws RegistrationExceptionToken {
    return registrationService.confirmToken(token);
  }

}
