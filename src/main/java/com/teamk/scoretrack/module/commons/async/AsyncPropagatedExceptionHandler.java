package com.teamk.scoretrack.module.commons.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public class AsyncPropagatedExceptionHandler implements AsyncUncaughtExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncPropagatedExceptionHandler.class);

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        LOGGER.error(String.format("Unhandled async exception thrown in method: %s with passed params: %s", method.getName(), Arrays.stream(params).map(Object::toString).collect(Collectors.joining(", "))), ex);
    }
}
