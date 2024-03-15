package com.teamk.scoretrack.module.commons.util;

import org.springframework.security.core.token.Sha512DigestUtils;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;

public final class CommonsUtil {

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

    public static String alphanumeric(int number, Random random) {
        String c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < number; i++) {
            sb.append(c.charAt(random.nextInt(c.length())));
        }
        return sb.toString();
    }

    public static String SRAlphanumeric(int number) {
        return alphanumeric(number, new SecureRandom());
    }

    public static String SRAlphanumeric(int low, int up) {
        SecureRandom random = new SecureRandom();
        return alphanumeric(random.nextInt(low, up), random);
    }

    public static String anonymize(String input, int start, int end) {
        return input.substring(0, start) + "*".repeat(end - start) + input.substring(end);
    }

    public static boolean isInRange(Instant time, Instant from, TemporalUnit unit, int fromAmount, int toAmount) {
        return isInRange(time, from.plus(fromAmount, unit), from.plus(toAmount, unit));
    }

    public static boolean isInRange(Instant time, Instant from, Instant to) {
        return from.isBefore(time) && to.isAfter(time);
    }

    public static boolean patternMatches(String input, String regexRule) {
        return Pattern.compile(regexRule).matcher(input).matches();
    }

    public static <T, U> U orNull(T toCheck, Function<T, U> nonNullCallback) {
        return toCheck != null ? nonNullCallback.apply(toCheck) : null;
    }

    public static <T> void runIfNonNull(T toCheck, Consumer<T> nonNullCallback) {
        if (toCheck != null) {
            nonNullCallback.accept(toCheck);
        }
    }
}
