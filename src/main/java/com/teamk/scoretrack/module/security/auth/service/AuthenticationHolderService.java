package com.teamk.scoretrack.module.security.auth.service;

import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationHolderService {
    public Optional<AuthenticationBean> getCurrentAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        return Optional.of((AuthenticationBean) authentication.getPrincipal());
    }
}
