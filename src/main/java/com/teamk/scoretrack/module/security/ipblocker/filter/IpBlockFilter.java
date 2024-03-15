package com.teamk.scoretrack.module.security.ipblocker.filter;

import com.teamk.scoretrack.module.security.commons.filter.BaseSecurityFilter;
import com.teamk.scoretrack.module.security.ipblocker.exception.IpBlockException;
import com.teamk.scoretrack.module.security.ipblocker.service.IpAuthenticationAttemptService;
import com.teamk.scoretrack.module.security.util.HttpUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class IpBlockFilter extends BaseSecurityFilter {
    private final IpAuthenticationAttemptService ipAuthenticationAttemptService;

    @Autowired
    public IpBlockFilter(IpAuthenticationAttemptService ipAuthenticationAttemptService) {
        this.ipAuthenticationAttemptService = ipAuthenticationAttemptService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String clientIP = HttpUtil.getClientIP(request);
        if (ipAuthenticationAttemptService.isBlocked(clientIP)) {
            throw new IpBlockException("Blocked IP address: %s".formatted(clientIP));
        }
        filterChain.doFilter(request, response);
    }
}
