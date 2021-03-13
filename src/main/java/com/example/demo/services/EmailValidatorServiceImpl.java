package com.example.demo.services;

import com.example.demo.exception.EmailIsNotValidException;
import com.example.demo.helpers.enums.AppMessages;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class EmailValidatorServiceImpl implements EmailValidatorService {


  public void test(String email) throws EmailIsNotValidException {
    if (!isValid(email)) {
      throw new EmailIsNotValidException(AppMessages.EMAIL_IS_NOT_VALID);
    }
  }

  private boolean isValid(String email) {
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
        "[a-zA-Z0-9_+&*-]+)*@" +
        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
        "A-Z]{2,7}$";
    Pattern pat = Pattern.compile(emailRegex);

    return pat.matcher(email).matches();
  }
}
