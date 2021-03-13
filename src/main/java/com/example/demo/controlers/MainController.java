package com.example.demo.controlers;
import com.example.demo.exception.EmailIsNotValidException;
import com.example.demo.exception.JmbgIsNotValidException;
import com.example.demo.exception.RegistrationException;
import com.example.demo.models.ExceptionDTO;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
public class MainController {
  @ExceptionHandler(EmailIsNotValidException.class)
  public ResponseEntity<ExceptionDTO> handlerEmail(EmailIsNotValidException exception) {
    String errorMessage = exception.getMessage();
    return new ResponseEntity<>(new ExceptionDTO(errorMessage), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(JmbgIsNotValidException.class)
  public ResponseEntity<ExceptionDTO> handlerJmbg(JmbgIsNotValidException exception) {
    String errorMessage = exception.getMessage();
    return new ResponseEntity<>(new ExceptionDTO(errorMessage), HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(RegistrationException.class)
  public ResponseEntity<ExceptionDTO> handlerRegE(RegistrationException exception) {
    String errorMessage = exception.getMessage();
    return new ResponseEntity<>(new ExceptionDTO(errorMessage), HttpStatus.BAD_REQUEST);
  }
}
