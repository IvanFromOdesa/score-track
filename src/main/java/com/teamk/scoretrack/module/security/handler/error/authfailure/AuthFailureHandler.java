package com.teamk.scoretrack.module.security.handler.error.authfailure;

import com.teamk.scoretrack.module.security.handler.error.authfailure.service.AuthenticationCredentialsFailureService;
import com.teamk.scoretrack.module.security.handler.error.authfailure.service.BadCredentialsAuthAttemptService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.teamk.scoretrack.module.security.auth.controller.AuthenticationController.LOGIN;

@Component(AuthFailureHandler.NAME)
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    public static final String NAME = "authFailureHandler";
    private final AuthenticationCredentialsFailureService authenticationCredentialsFailureService;
    private final BadCredentialsAuthAttemptService badCredentialsAuthAttemptService;

    @Autowired
    public AuthFailureHandler(AuthenticationCredentialsFailureService authenticationCredentialsFailureService, BadCredentialsAuthAttemptService badCredentialsAuthAttemptService) {
        super(LOGIN.concat("?error"));
        this.authenticationCredentialsFailureService = authenticationCredentialsFailureService;
        this.badCredentialsAuthAttemptService = badCredentialsAuthAttemptService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof BadCredentialsException) {
            String loginname = request.getParameter("username");
            if (badCredentialsAuthAttemptService.incrementFailureAttempt(loginname)) {
                authenticationCredentialsFailureService.saveByLoginname(loginname);
            }
        }
        super.onAuthenticationFailure(request, response, exception);
    }
}
