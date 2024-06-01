package com.teamk.scoretrack.module.security.handler;

import org.springframework.web.servlet.HandlerInterceptor;

public interface ExtendedHandlerInterceptor extends HandlerInterceptor {
    String[] getPathPatterns();
}
