package com.teamk.scoretrack.module.jwt;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.token.jwt.model.AccessToken;
import com.teamk.scoretrack.module.security.token.jwt.model.JwtSource;
import com.teamk.scoretrack.module.security.token.jwt.service.JWTResolverService;
import com.teamk.scoretrack.utils.RsaTestUtils;
import com.teamk.scoretrack.utils.AuthenticationBeanUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JwtAuthenticationTokenTests {
    private JWTResolverService jwtResolverService;
    private JwtDecoder jwtDecoder;

    @BeforeEach
    void setUp() throws GeneralSecurityException, IOException {
        jwtResolverService = new JWTResolverService(jwtEncoder());
        jwtDecoder = NimbusJwtDecoder.withPublicKey(getPublicKey()).build();
        ReflectionTestUtils.setField(jwtResolverService, "issuer", "score-track-test");
    }

    private JwtEncoder jwtEncoder() throws GeneralSecurityException, IOException {
        JWK jwk = new RSAKey.Builder(getPublicKey()).privateKey(getPrivateKey()).build();
        return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(jwk)));
    }

    private RSAPrivateKey getPrivateKey() throws GeneralSecurityException, IOException {
        return RsaTestUtils.getPrivateKey(Objects.requireNonNull(this.getClass().getClassLoader().getResource("certs/pr_test.pem")).getPath());
    }

    private RSAPublicKey getPublicKey() throws GeneralSecurityException, IOException {
        return RsaTestUtils.getPublicKey(Objects.requireNonNull(this.getClass().getClassLoader().getResource("certs/pb_test.pem")).getPath());
    }

    @Test
    void shouldDecodeJwt_whenGivenValidToken() {
        String value = getAccessToken().value();
        Jwt jwt = decode(value);
        assertEquals(jwt.getTokenValue(), value);
    }

    @Test
    void shouldExtractEmailClaim_whenGivenValidToken() {
        Object o = getClaim(AccessToken.Claims.EMAIL.name());
        assertDoesNotThrow(() -> AccessToken.Claims.EMAIL.cast().apply((String) o));
    }

    @Test
    void shouldExtractExternalId_whenGivenValidToken() {
        Object o = getClaim(AccessToken.Claims.EXTERNAL_ID.name());
        assertDoesNotThrow(() -> AccessToken.Claims.EXTERNAL_ID.cast().apply((String) o));
    }

    @Test
    void shouldExtractPreferredLanguage_whenGivenValidToken() {
        Object o = getClaim(AccessToken.Claims.PREFERRED_LANGUAGE.name());
        assertDoesNotThrow(() -> AccessToken.Claims.PREFERRED_LANGUAGE.cast().apply((String) o));
    }

    private Object getClaim(String name) {
        return getJwt().getClaims().get(name);
    }

    private Jwt decode(String value) {
        return jwtDecoder.decode(value);
    }

    private Jwt getJwt() {
        return decode(getAccessToken().value());
    }

    private AccessToken getAccessToken() {
        return jwtResolverService.generateToken(getJwtSrc());
    }

    private JwtSource getJwtSrc() {
        AuthenticationBean authenticationBean = AuthenticationBeanUtils.mockAuthenticationBean();
        Instant now = Instant.now();
        return new JwtSource(authenticationBean, now, now.plus(5, ChronoUnit.MINUTES));
    }
}
