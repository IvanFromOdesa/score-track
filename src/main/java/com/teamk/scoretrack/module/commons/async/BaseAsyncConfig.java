package com.teamk.scoretrack.module.commons.async;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    @Bean(name = "baseThreadPoolExecutor")
    public ExecutorService baseThreadPoolExecutor() {
        return new ThreadPoolExecutor(10, 50, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncPropagatedExceptionHandler();
    }

    @Override
    public Executor getAsyncExecutor() {
        return baseExecutorService();
    }
}
