package com.example.demo.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsernameAndPasswordAuthenticationRequest {
   private String username;
   private  String password;
}
