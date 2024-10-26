package com.teamk.scoretrack.module.security.token.crypto;

import com.teamk.scoretrack.module.commons.token.ITokenResolver;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.security.SecureRandom;

@Service
public class SecureRandomTokenService implements ITokenResolver<String, SecureRandomTokenInput> {
    private static final char[] CHARS;

    static {
        CHARS = new char[94];
        for (int i = 0, ch = 33; ch <= 126; i++, ch ++) {
            CHARS[i] = (char) ch;
        }
    }

    public String generateToken(SecureRandomTokenInput input) {
        SecureRandom secureRandom = new SecureRandom();
        long requestTimeMillis = input.requestTime().toEpochMilli();
        byte[] ipAddressBytes = input.ipAddress().getBytes();
        byte[] emailBytes = input.email().getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES
                + ipAddressBytes.length + emailBytes.length
                + Integer.BYTES);
        buffer.putLong(requestTimeMillis);
        buffer.put(ipAddressBytes);
        buffer.put(emailBytes);
        buffer.putInt(secureRandom.nextInt());
        secureRandom.setSeed(buffer.array());
        int tokenLength = 64;
        StringBuilder sb = new StringBuilder(tokenLength);
        for (int i = 0; i < tokenLength; i ++) {
            sb.append(CHARS[secureRandom.nextInt(CHARS.length)]);
        }
        return sb.toString();
    }
}
