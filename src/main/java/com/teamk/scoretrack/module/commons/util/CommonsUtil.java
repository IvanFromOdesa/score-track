package com.teamk.scoretrack.module.commons.util;

import org.springframework.security.core.token.Sha512DigestUtils;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class CommonsUtil {

    public static <T> T[] concat2Arrays(T[] first, T[] second) {
        T[] both = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, both, first.length, second.length);
        return both;
    }

    public static String randomNChars(int number) {
        String s = Sha512DigestUtils.shaHex(UUID.randomUUID().toString());
        Random random = new Random();
        int beginIndex = random.nextInt(0, number);
        while (s.length() - beginIndex < number) {
            beginIndex = random.nextInt(0, number);
        }
        return s.substring(beginIndex, number + beginIndex);
    }

    public static String anonymize(String input, int start, int end) {
        return input.substring(0, start) + "*".repeat(end - start) + input.substring(end);
    }
}
