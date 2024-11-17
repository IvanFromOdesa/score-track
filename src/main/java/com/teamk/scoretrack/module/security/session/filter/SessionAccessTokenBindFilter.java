package com.teamk.scoretrack.module.security.session.filter;

import com.teamk.scoretrack.module.security.commons.filter.BaseSecurityFilter;
import com.teamk.scoretrack.module.security.session.exception.IllegalAccessTokenException;
import com.teamk.scoretrack.module.security.token.jwt.model.AccessToken;
import com.teamk.scoretrack.module.security.util.HttpUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.teamk.scoretrack.module.core.api.commons.init.controller.ApiInitController.SUPPORTED_APIS;

@Component
public class SessionAccessTokenBindFilter extends BaseSecurityFilter {
    public static final String NAME = "SESSION_BOUND_ACCESS_TOKEN";
    private final BearerTokenResolver bearerTokenResolver;

    public SessionAccessTokenBindFilter() {
        this.bearerTokenResolver = new DefaultBearerTokenResolver();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = bearerTokenResolver.resolve(request);
        if (token == null) {
            filterChain.doFilter(request, response);
        } else {
            HttpSession session = request.getSession();
            Object boundToken = session.getAttribute(NAME);
            if (boundToken == null) {
                throw new IllegalAccessTokenException("No bound token for session %s exists.".formatted(session));
            } else {
                if (!((AccessToken) boundToken).value().equals(token)) {
                    throw new IllegalAccessTokenException("Provided access token does not correspond to session %s bound".formatted(session));
                }
            }
            filterChain.doFilter(request, response);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return super.shouldNotFilter(request) || HttpUtil.getRequestedUri(request).endsWith(SUPPORTED_APIS);
    }
}
