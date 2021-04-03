package com.example.demo.services;

import com.example.demo.exception.EmailIsNotValidException;
import com.example.demo.helpers.enums.AppMessages;
import com.example.demo.models.EmailMessage;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@AllArgsConstructor
@Log
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender javaMailSender;

  public void send(EmailMessage emailMessage) {
    try{
      MimeMessage mimeMessage= javaMailSender.createMimeMessage();
      MimeMessageHelper helper= new MimeMessageHelper(mimeMessage,"utf-8");
      helper.setText(emailMessage.getMessage(),emailMessage.getToHtml());
      helper.setTo(emailMessage.getTo());
      helper.setSubject(emailMessage.getSubject());
      //javaMailSender.send(mimeMessage);
    }

    catch(MessagingException e){
      log.info("porika nije poslata");
    }
  }

  @Override
  public void testEmailAddress(String email) throws EmailIsNotValidException {
    if (!isValid(email)) {
      throw new EmailIsNotValidException(AppMessages.EMAIL_IS_NOT_VALID);
    }
  }

  private boolean isValid(String email) {
    String emailRegex =
        "^[a-zA-Z0-9_+&*-]+(?:\\."
            + "[a-zA-Z0-9_+&*-]+)*@"
            + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
            + "A-Z]{2,7}$";
    Pattern pat = Pattern.compile(emailRegex);

    return pat.matcher(email).matches();
  }
}
