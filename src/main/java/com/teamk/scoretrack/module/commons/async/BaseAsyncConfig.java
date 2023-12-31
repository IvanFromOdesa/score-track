package com.teamk.scoretrack.module.commons.async;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Configuration for asynchronous 'task executors'
 */
@Configuration
@EnableAsync
public class BaseAsyncConfig implements AsyncConfigurer {

    @Bean(name = "fixedThreadPool")
    public Executor baseExecutorService() {
        return Executors.newFixedThreadPool(8);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncPropagatedExceptionHandler();
    }
}
