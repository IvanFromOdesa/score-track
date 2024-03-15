package com.teamk.scoretrack.module.security.ipblocker;

import com.teamk.scoretrack.module.security.commons.ForbiddenResponseException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

public class ExtendedLoginUrlAuthEntryPoint extends LoginUrlAuthenticationEntryPoint {
    public static final String NAME = "extendedLoginUrlAuthEntryPoint";
    private final HandlerExceptionResolver exceptionResolver;
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtendedLoginUrlAuthEntryPoint.class);

    public ExtendedLoginUrlAuthEntryPoint(String loginFormUrl, HandlerExceptionResolver exceptionResolver) {
        super(loginFormUrl);
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (authException instanceof ForbiddenResponseException e) {
            LOGGER.warn(authException.getMessage());
            exceptionResolver.resolveException(request, response, null, e);
        } else {
            super.commence(request, response, authException);
        }
    }
}
