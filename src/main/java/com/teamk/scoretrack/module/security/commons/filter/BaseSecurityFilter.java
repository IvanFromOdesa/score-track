package com.teamk.scoretrack.module.security.commons.filter;

import com.teamk.scoretrack.module.security.util.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Set;

public abstract class BaseSecurityFilter extends OncePerRequestFilter {
    protected static final Set<String> _WHITELISTED = Set.of("/favicon.ico");

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return _WHITELISTED.stream().anyMatch(r -> HttpUtil.getRequestedUri(request).endsWith(r));
    }
}
