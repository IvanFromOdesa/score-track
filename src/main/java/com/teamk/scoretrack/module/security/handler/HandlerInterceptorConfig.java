package com.teamk.scoretrack.module.security.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class HandlerInterceptorConfig implements WebMvcConfigurer {
    private final List<ExtendedHandlerInterceptor> interceptors;

    @Autowired
    public HandlerInterceptorConfig(List<ExtendedHandlerInterceptor> interceptors) {
        this.interceptors = interceptors;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Add interceptors for resources that are not supposed to be accessed directly
        interceptors.forEach(i -> registry.addInterceptor(i).addPathPatterns(i.getPathPatterns()));
    }
}
