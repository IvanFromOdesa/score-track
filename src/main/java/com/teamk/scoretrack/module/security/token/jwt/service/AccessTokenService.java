package com.teamk.scoretrack.module.security.token.jwt.service;

import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationHolderService;
import com.teamk.scoretrack.module.security.token.jwt.model.AccessToken;
import com.teamk.scoretrack.module.security.token.jwt.model.JwtSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class AccessTokenService {
    private final JWTResolverService jwtResolverService;
    private final AuthenticationHolderService authenticationHolderService;

    @Autowired
    public AccessTokenService(JWTResolverService jwtResolverService, AuthenticationHolderService authenticationHolderService) {
        this.jwtResolverService = jwtResolverService;
        this.authenticationHolderService = authenticationHolderService;
    }

    /**
     * Generates access token. This should be used only from base security filter chain (no "/api" prefix)
     * as if the authentication is anonymous or JWT one, returns empty {@link Optional}.
     * @return optional of generated token, if accessed via base security filter chain, or empty optional.
     */
    public Optional<AccessToken> generateToken() {
        Optional<AuthenticationBean> currentAuthentication = authenticationHolderService.getCurrentAuthentication();
        if (currentAuthentication.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(generateToken(currentAuthentication.get()));
    }

    public AccessToken generateToken(AuthenticationBean authenticationBean) {
        Instant now = Instant.now();
        return jwtResolverService.generateToken(new JwtSource(authenticationBean, now, now.plus(5, ChronoUnit.MINUTES)));
    }
}
