package com.teamk.scoretrack.module.security.handler;

import com.teamk.scoretrack.module.security.token.otp.controller.OtpAuthController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class HandlerInterceptorConfig implements WebMvcConfigurer {
    private final RecoverRequestInterceptor recoverRequestInterceptor;

    @Autowired
    public HandlerInterceptorConfig(RecoverRequestInterceptor recoverRequestInterceptor) {
        this.recoverRequestInterceptor = recoverRequestInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(recoverRequestInterceptor).addPathPatterns(OtpAuthController.RECOVER.concat("/**"));
    }
}
