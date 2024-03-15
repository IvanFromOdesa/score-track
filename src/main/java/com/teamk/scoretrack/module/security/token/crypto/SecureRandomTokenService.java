package com.teamk.scoretrack.module.security.token.crypto;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class SecureRandomTokenService {
    private static final char[] CHARS = new char[94];
    /* Initialize chars array */
    static {
        int idx = 0;
        for (int ch = 33; ch <= 126; ch ++) {
            CHARS[idx] = (char) ch;
            idx ++;
        }
    }

    public String generateToken() {
        StringBuilder sb = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < secureRandom.nextInt(64,96); i ++) {
            sb.append(CHARS[secureRandom.nextInt(CHARS.length)]);
        }
        return sb.toString();
    }
}
