package com.teamk.scoretrack.module.commons.cache.redis;

import com.teamk.scoretrack.module.commons.util.log.MessageLogger;
import org.springframework.util.ErrorHandler;

public class RedisMessageProcessingErrorHandler implements ErrorHandler {
    @Override
    public void handleError(Throwable t) {
        // TODO: send to monitoring system
        MessageLogger.error("Unexpected error during Redis message processing.", t);
    }
}
