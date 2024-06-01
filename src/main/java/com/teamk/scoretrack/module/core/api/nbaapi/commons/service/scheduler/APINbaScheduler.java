package com.teamk.scoretrack.module.core.api.nbaapi.commons.service.scheduler;

import com.teamk.scoretrack.module.commons.mongo.scheduler.AbstractUpdateScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public abstract class APINbaScheduler extends AbstractUpdateScheduler {
    protected final APINbaSchedulerService schedulerService;

    protected APINbaScheduler(APINbaSchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @Override
    @Autowired
    protected void setTaskScheduler(@Qualifier(APINbaSchedulerConfig.NAME) ThreadPoolTaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @Override
    public void update() {
        schedulerService.execute();
    }
}
