package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.service.scheduler;

import com.teamk.scoretrack.module.core.api.nbaapi.commons.service.scheduler.APINbaScheduler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PlayerScheduler extends APINbaScheduler {
    @Value("${nbaapi.update.player.cron}")
    private String cron;

    protected PlayerScheduler(PlayerSchedulerService schedulerService) {
        super(schedulerService);
    }

    @Override
    public String getCron() {
        return cron;
    }
}
