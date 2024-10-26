package com.teamk.scoretrack.module.security.token.crypto;

import java.time.Instant;

public record SecureRandomTokenInput(String ipAddress, String email, Instant requestTime) {
}
