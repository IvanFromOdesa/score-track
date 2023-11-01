package com.teamk.scoretrack.module.core.api.nbaapi.service.scheduler;

import com.teamk.scoretrack.module.core.api.nbaapi.service.APINbaUpdateEntityService;
import com.teamk.scoretrack.module.core.api.nbaapi.service.external.APINbaExternalService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class APINbaSchedulerService {
    @Autowired
    protected APINbaUpdateEntityService updateService;
    @Autowired
    protected APINbaExternalService externalService;

    protected abstract void startUpdate();

    protected abstract String getCollectionName();

    public void execute() {
        if (updateService.isUpdatePossible(getCollectionName())) {
            startUpdate();
        }
    }
}
