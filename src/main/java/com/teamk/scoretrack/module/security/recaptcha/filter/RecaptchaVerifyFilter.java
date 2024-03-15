package com.teamk.scoretrack.module.security.recaptcha.filter;

import com.teamk.scoretrack.module.security.history.domain.AuthenticationHistory;
import com.teamk.scoretrack.module.security.recaptcha.service.RecaptchaResponseResolveService;
import com.teamk.scoretrack.module.security.token.otp.controller.OtpAuthController;
import com.teamk.scoretrack.module.security.token.otp.controller.OtpRedirectHandler;
import com.teamk.scoretrack.module.security.util.HttpUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RecaptchaVerifyFilter extends OncePerRequestFilter {
    private final RecaptchaResponseResolveService recaptchaResponseResolveService;

    @Autowired
    public RecaptchaVerifyFilter(RecaptchaResponseResolveService recaptchaResponseResolveService) {
        this.recaptchaResponseResolveService = recaptchaResponseResolveService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        AuthenticationHistory.Status status = recaptchaResponseResolveService.resolve(request, HttpUtil.getClientIP(request));
        if (status.isBlocked()) {
            OtpRedirectHandler.onBlockStatus(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getMethod().equals("POST") || HttpUtil.getRequestedUri(request).endsWith(OtpAuthController.RECOVER);
    }
}
