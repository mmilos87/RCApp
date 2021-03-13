package com.example.demo.services;

import com.example.demo.models.EmailMessage;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

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

}
