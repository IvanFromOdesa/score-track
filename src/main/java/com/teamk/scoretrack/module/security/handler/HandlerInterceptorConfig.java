package com.teamk.scoretrack.module.security.handler;

import com.teamk.scoretrack.module.security.auth.controller.AuthenticationController;
import com.teamk.scoretrack.module.security.auth.controller.AuthenticationFailureInterceptor;
import com.teamk.scoretrack.module.security.token.otp.controller.OtpAuthController;
import com.teamk.scoretrack.module.security.token.otp.controller.RecoverRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class HandlerInterceptorConfig implements WebMvcConfigurer {
    private final RecoverRequestInterceptor recoverRequestInterceptor;
    private final AuthenticationFailureInterceptor authenticationFailureInterceptor;

    @Autowired
    public HandlerInterceptorConfig(RecoverRequestInterceptor recoverRequestInterceptor, AuthenticationFailureInterceptor authenticationFailureInterceptor) {
        this.recoverRequestInterceptor = recoverRequestInterceptor;
        this.authenticationFailureInterceptor = authenticationFailureInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Add interceptors for resources that are not supposed to be accessed directly
        registry.addInterceptor(recoverRequestInterceptor).addPathPatterns(OtpAuthController.RECOVER.concat("/**"));
        registry.addInterceptor(authenticationFailureInterceptor).addPathPatterns(AuthenticationController.LOGIN.concat("/**"));
    }
}
