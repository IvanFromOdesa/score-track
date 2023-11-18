package com.teamk.scoretrack.module.security.service.jwt;

import com.teamk.scoretrack.module.commons.token.ITokenResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.teamk.scoretrack.module.core.entities.Privileges.SCOPE_PREFIX;

@Service
public class JWTResolverService implements ITokenResolver<String, Authentication> {
    private static final long JWT_LIFETIME = 1;
    private final JwtEncoder jwtEncoder;
    @Value("${spring.application.name}")
    private String issuer;

    @Autowired
    public JWTResolverService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public String generateToken(Authentication authentication) {
        String[] privileges = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plus(JWT_LIFETIME, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim(SCOPE_PREFIX, privileges)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
