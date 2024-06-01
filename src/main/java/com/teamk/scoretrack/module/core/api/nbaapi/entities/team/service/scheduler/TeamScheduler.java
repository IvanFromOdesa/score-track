package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.scheduler;

import com.teamk.scoretrack.module.core.api.nbaapi.commons.service.scheduler.APINbaScheduler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TeamScheduler extends APINbaScheduler {
    @Value("${nbaapi.update.team.cron}")
    private String cron;

    protected TeamScheduler(TeamSchedulerService schedulerService) {
        super(schedulerService);
    }

    @Override
    public String getCron() {
        return cron;
    }
}
