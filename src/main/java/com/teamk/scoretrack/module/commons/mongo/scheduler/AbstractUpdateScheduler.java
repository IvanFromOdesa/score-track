package com.teamk.scoretrack.module.commons.mongo.scheduler;

import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

public abstract class AbstractUpdateScheduler {
    protected ThreadPoolTaskScheduler taskScheduler;

    protected abstract void setTaskScheduler(ThreadPoolTaskScheduler taskScheduler);
    public abstract void update();
    public abstract String getCron();

    @PostConstruct
    private void execute() {
        taskScheduler.schedule(this::update, new CronTrigger(getCron()));
    }
}
