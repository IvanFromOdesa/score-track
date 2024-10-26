package com.teamk.scoretrack.module.security.commons.filter;

import com.teamk.scoretrack.module.commons.exception.ServerException;
import com.teamk.scoretrack.module.security.commons.config.SecurityConfiguration;
import com.teamk.scoretrack.module.security.commons.model.SessionRedirect;
import com.teamk.scoretrack.module.security.util.HttpUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Set;

@Component
public class RedirectFilter extends BaseSecurityFilter {
    private final Set<SessionRedirect> sessionRedirects;

    @Autowired
    public RedirectFilter(Set<SessionRedirect> sessionRedirects) {
        this.sessionRedirects = sessionRedirects;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Enumeration<String> attributeNames = request.getSession().getAttributeNames();

        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            sessionRedirects.stream()
                    .filter(r -> r.attributeName().equals(name))
                    .findFirst()
                    .ifPresent(redirect -> {
                        try {
                            response.sendRedirect(redirect.path());
                        } catch (IOException e) {
                            throw new ServerException(e);
                        }
                    });
        }

        if (!response.isCommitted()) {
            filterChain.doFilter(request, response);
        }
    }

    // TODO: skip whitelisted in all custom filters?
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return super.shouldNotFilter(request) ||
                sessionRedirects.stream().anyMatch(sr -> HttpUtil.getRequestedUri(request).endsWith(sr.path())) ||
                Arrays.stream(SecurityConfiguration.getWhitelistedResources()).anyMatch(r -> new AntPathMatcher().match(r, request.getRequestURI()));
    }
}
