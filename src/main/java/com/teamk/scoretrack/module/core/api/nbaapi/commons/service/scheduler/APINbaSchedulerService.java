package com.teamk.scoretrack.module.core.api.nbaapi.commons.service.scheduler;

import com.teamk.scoretrack.module.commons.other.ErrorMap;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.domain.APINbaUpdate;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.service.APINbaUpdateEntityService;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.service.exception.APINbaUpdateFailureException;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.service.external.APINbaExternalService;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.SeasonUpdateOptions;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.SeasonUpdateStrategy;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.SupportedSeasons;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public abstract class APINbaSchedulerService {
    @Autowired
    protected APINbaUpdateEntityService updateService;
    @Autowired
    protected APINbaExternalService externalService;

    /**
     * Updates the collection.
     * @param seasonsToUpdate seasons to update the specific data
     * @param updateStatus update status of the process
     * @return update status
     */
    protected abstract APINbaUpdate.Status startUpdate(List<SupportedSeasons> seasonsToUpdate, AtomicReference<APINbaUpdate.Status> updateStatus);

    protected abstract String getCollectionName();

    protected abstract SeasonUpdateStrategy getSeasonUpdateStrategy();

    public void execute() {
        String collectionName = this.getCollectionName();
        Optional<APINbaUpdate> latestUpdate = updateService.findLatestUpdate(collectionName);
        List<SupportedSeasons> updatedSeasons = new ArrayList<>();
        if (latestUpdate.isPresent()) {
            APINbaUpdate apiNbaUpdate = latestUpdate.get();
            if (apiNbaUpdate.getStatus().isFinished()) {
                updatedSeasons.addAll(apiNbaUpdate.getUpdatedSeasons());
            } else {
                throw new APINbaUpdateFailureException("Previous update finished with errors on: %s".formatted(apiNbaUpdate.getFinished()));
            }
        }
        UpdateReadinessStatus updateReadinessStatus = this.getUpdateReadinessStatus();
        if (!updateReadinessStatus.updatePossible) {
            throw new APINbaUpdateFailureException("Update not possible due to errors: %s".formatted(updateReadinessStatus.errors.getErrors()));
        }
        SeasonUpdateOptions seasonUpdateOptions = getSeasonUpdateStrategy().getSeasonsToUpdate(updatedSeasons);
        if (!externalService.isEnoughQuotasLeft(seasonUpdateOptions.requiredRequestQuotas())) {
            throw new APINbaUpdateFailureException("Not enough request quotas left for update execution: %s".formatted(collectionName));
        }
        AtomicReference<APINbaUpdate.Status> updateStatus = new AtomicReference<>(APINbaUpdate.Status.PROCESSING);
        Instant started = Instant.now();
        updateService.save(new APINbaUpdate(started, null, collectionName, updateStatus.get()));
        List<SupportedSeasons> seasonsToUpdate = seasonUpdateOptions.seasonsToUpdate();
        APINbaUpdate.Status status = startUpdate(seasonsToUpdate, updateStatus);
        updateService.update(started, collectionName, getApiNbaUpdate(status, seasonsToUpdate));
    }

    protected UpdateReadinessStatus getUpdateReadinessStatus() {
        return new UpdateReadinessStatus(true, ErrorMap.empty());
    }

    private static APINbaUpdate getApiNbaUpdate(APINbaUpdate.Status status, List<SupportedSeasons> seasonsToUpdate) {
        APINbaUpdate apiNbaUpdate = new APINbaUpdate(Instant.now(), status.isWithErrors() ? status : APINbaUpdate.Status.FINISHED);
        apiNbaUpdate.getUpdatedSeasons().addAll(seasonsToUpdate);
        return apiNbaUpdate;
    }

    public record UpdateReadinessStatus(boolean updatePossible, ErrorMap errors) {

    }
}
