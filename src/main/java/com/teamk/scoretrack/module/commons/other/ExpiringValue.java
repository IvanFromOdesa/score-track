package com.teamk.scoretrack.module.commons.other;

import java.util.concurrent.TimeUnit;

public class ExpiringValue<T> {
    private final T value;
    private final long expirationTime;

    public ExpiringValue(T value, long expirationTime, TimeUnit timeUnit) {
        this.value = value;
        this.expirationTime = System.currentTimeMillis() + timeUnit.toMillis(expirationTime);
    }

    public T getValue() {
        return value;
    }

    public long getExpirationTime() {
        return expirationTime;
    }
}
