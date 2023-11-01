package com.teamk.scoretrack.module.commons.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: improve
/**
 * System logger. Used mainly to log common exceptions (e.g. file not found on fs) or 'for compile checks'.
 * May be used in the future to do custom events like sending emails to admins in case of really 'bad' exception.
 */
public class MessageLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageLogger.class);

    public static void info(String msg) {
        LOGGER.info(msg);
    }

    public static void error(String msg) {
        LOGGER.error(msg);
    }

    public static void error(String msg, Throwable ex) {
        LOGGER.error(msg, ex);
    }
}
