package com.teamk.scoretrack.module.security.auth.service;

import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationWrapper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import static com.teamk.scoretrack.module.security.token.jwt.model.AccessToken.Claims.*;

@Service
public class AuthenticationHolderService {
    private static final String ANONYMOUS_AUTHENTICATION = "anonymousUser";

    public Optional<AuthenticationBean> getCurrentAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isAnonymousAuthentication(authentication) || authentication instanceof JwtAuthenticationToken) {
            return Optional.empty();
        }
        return Optional.of((AuthenticationBean) authentication.getPrincipal());
    }

    public Optional<JwtAuthenticationToken> getCurrentAuthenticationToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isAnonymousAuthentication(authentication) || authentication instanceof UsernamePasswordAuthenticationToken) {
            return Optional.empty();
        }
        return Optional.of((JwtAuthenticationToken) authentication);
    }

    public Optional<AuthenticationWrapper> getAuthenticationWrapper() {
        Optional<AuthenticationBean> currentAuthentication = getCurrentAuthentication();
        if (currentAuthentication.isPresent()) {
            AuthenticationBean authenticationBean = currentAuthentication.get();
            return Optional.of(new AuthenticationWrapper(authenticationBean.getExternalId(), authenticationBean.getLoginname(), authenticationBean.getEmail(), authenticationBean.getUser().getPreferredLang(), authenticationBean.getAuthorities()));
        } else {
            Optional<JwtAuthenticationToken> currentAuthenticationToken = getCurrentAuthenticationToken();
            if (currentAuthenticationToken.isPresent()) {
                JwtAuthenticationToken jwtAuthenticationToken = currentAuthenticationToken.get();
                Map<String, Object> claims = jwtAuthenticationToken.getTokenAttributes();
                return Optional.of(new AuthenticationWrapper(EXTERNAL_ID.extractClaim(claims, String.class), jwtAuthenticationToken.getName(), EMAIL.extractClaim(claims, String.class), PREFERRED_LANGUAGE.extractClaim(claims, String.class), jwtAuthenticationToken.getAuthorities()));
            }
        }
        return Optional.empty();
    }

    public static boolean isAnonymousAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return isAnonymousAuthentication(authentication);
    }

    public static boolean isAnonymousAuthentication(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken || authentication.getName().equals(ANONYMOUS_AUTHENTICATION);
    }
}
