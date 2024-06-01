package com.teamk.scoretrack.module.commons.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.teamk.scoretrack.module.commons.util.log.MessageLogger;
import org.springframework.security.core.token.Sha512DigestUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
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

    public static <T> List<T> readResourcesIntoCollection(ObjectMapper mapper, String path, Class<T> tClass) {
        URL resource = CommonsUtil.class.getClassLoader().getResource(path);
        if (resource != null) {
            File file = new File(resource.getFile());
            try {
                TypeFactory typeFactory = mapper.getTypeFactory();
                return mapper.readValue(file, typeFactory.constructCollectionType(List.class, tClass));
            } catch (IOException e) {
                MessageLogger.error(e.getMessage(), e);
            }
        } else {
            MessageLogger.error("Can't get the resource file from FS: %s".formatted(path));
        }
        return new ArrayList<>();
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
        return orNull(toCheck, nonNullCallback, t -> true);
    }

    public static <T, U> U orNull(T toCheck, Function<T, U> nonNullCallback, Predicate<T> additionalCheck) {
        return toCheck != null && additionalCheck.test(toCheck) ? nonNullCallback.apply(toCheck) : null;
    }

    public static <T> void runIfNonNull(T toCheck, Consumer<T> nonNullCallback) {
        runIfNonNull(toCheck, nonNullCallback, t -> true);
    }

    public static <T> void runIfNonNull(T toCheck, Consumer<T> nonNullCallback, Predicate<T> additionalCheck) {
        if (toCheck != null && additionalCheck.test(toCheck)) {
            nonNullCallback.accept(toCheck);
        }
    }
}
