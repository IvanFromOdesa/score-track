package com.teamk.scoretrack.module.security.token.jwt.service;

import com.teamk.scoretrack.module.commons.token.ITokenResolver;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.token.jwt.model.AccessToken;
import com.teamk.scoretrack.module.security.token.jwt.model.JwtSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import static com.teamk.scoretrack.module.security.token.jwt.model.AccessToken.Claims.*;


@Service
public class JWTResolverService implements ITokenResolver<AccessToken, JwtSource> {
    private final JwtEncoder jwtEncoder;
    @Value("${spring.application.name}")
    private String issuer;

    @Autowired
    public JWTResolverService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public AccessToken generateToken(JwtSource source) {
        AuthenticationBean authentication = source.authenticationBean();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(source.issuedAt())
                .expiresAt(source.expiresAt())
                .subject(authentication.getUsername())
                .claim(SCOPE_PREFIX, authentication.getAuthorities())
                .claim(EMAIL.name(), authentication.getEmail())
                .claim(EXTERNAL_ID.name(), authentication.getExternalId())
                .claim(PREFERRED_LANGUAGE.name(), authentication.getUser().getPreferredLang().getAlias())
                .build();
        return new AccessToken(jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(), source.expiresAt());
    }
}
