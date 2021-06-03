package com.example.demo.security.config;

import com.example.demo.jwt.JwtConfig;
import com.example.demo.jwt.JwtTokenHelper;
import com.example.demo.jwt.JwtTokenVerifier;
import com.example.demo.jwt.JwtUsernamePasswordAndDeviceAuthenticationFilter;
import com.example.demo.services.RcDeviceAndLocationService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.crypto.SecretKey;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService appUserService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final SecretKey secretKey;
  private final JwtConfig jwtConfig;
  private final JwtTokenHelper jwtTokenHelper;
  private final RcDeviceAndLocationService deviceService;
  private final RcAuthenticationEventPublisher rcAuthenticationEventPublisher;
  private final AuthenticationSuccessHandler authenticationSuccessHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilter(
            new JwtUsernamePasswordAndDeviceAuthenticationFilter(jwtConfig, authenticationManager(),
                jwtTokenHelper,deviceService))
        .addFilterAfter(new JwtTokenVerifier(jwtConfig, secretKey, jwtTokenHelper),
            JwtUsernamePasswordAndDeviceAuthenticationFilter.class)
        .authorizeRequests()
        .antMatchers("/api/v*/registration/**").permitAll()
        //  .antMatchers( "/api/**").permitAll()//jwt is turned off
        .anyRequest()
        .authenticated().and()
            .oauth2Login()
            .successHandler(authenticationSuccessHandler);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(daoAuthenticationProvider());
    auth.authenticationEventPublisher(rcAuthenticationEventPublisher);
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(bCryptPasswordEncoder);
    provider.setUserDetailsService(appUserService);
    return provider;
  }
}
