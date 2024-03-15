package com.teamk.scoretrack.module.core.api.nbaapi.commons.service.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class APINbaSchedulerConfig {
    public static final String NAME = "api-nba-update-scheduler";

    @Value("${nbaapi.update.threadpool.size}")
    private int size;

    @Bean(NAME)
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(size);
        threadPoolTaskScheduler.setThreadNamePrefix(NAME);
        return threadPoolTaskScheduler;
    }
}
