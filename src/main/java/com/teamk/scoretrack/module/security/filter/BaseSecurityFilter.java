package com.teamk.scoretrack.module.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Set;

public abstract class BaseSecurityFilter extends OncePerRequestFilter {
    protected static final Set<String> _WHITELISTED = Set.of("/favicon.ico");

    protected String getRequestedUri(HttpServletRequest request) {
        return ServletUriComponentsBuilder.fromRequestUri(request).build().toUriString();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return _WHITELISTED.stream().anyMatch(r -> getRequestedUri(request).endsWith(r));
    }
}
