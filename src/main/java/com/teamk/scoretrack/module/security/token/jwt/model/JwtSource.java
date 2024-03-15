package com.teamk.scoretrack.module.security.token.jwt.model;

import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;

import java.time.Instant;

public record JwtSource(AuthenticationBean authenticationBean, Instant issuedAt, Instant expiresAt) {
}
