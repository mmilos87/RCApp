package com.example.demo.security.config;

import com.example.demo.services.LoginAttemptService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RcAuthenticationEventPublisher implements AuthenticationEventPublisher {
    private LoginAttemptService loginAttemptService;

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        String email =
                authentication.getPrincipal() instanceof DefaultOidcUser
                        ? ((DefaultOidcUser) authentication.getPrincipal()).getEmail()
                        : authentication.getName();
        loginAttemptService.successLoginAttempt(email);
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException e, Authentication authentication) {
        loginAttemptService.failureLoginAttempt(authentication.getName());
        throw new IllegalStateException(e.getMessage());
        // todo obraditi izuzetke
    }
}
