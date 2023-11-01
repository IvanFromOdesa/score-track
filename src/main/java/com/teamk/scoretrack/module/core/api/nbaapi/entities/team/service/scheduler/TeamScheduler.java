package com.teamk.scoretrack.module.core.api.nbaapi.entities.team.service.scheduler;

import com.teamk.scoretrack.module.core.api.nbaapi.service.scheduler.APINbaScheduler;
import org.springframework.stereotype.Service;

@Service
public class TeamScheduler extends APINbaScheduler {
    protected TeamScheduler(TeamSchedulerService schedulerService) {
        super(schedulerService);
    }
}
