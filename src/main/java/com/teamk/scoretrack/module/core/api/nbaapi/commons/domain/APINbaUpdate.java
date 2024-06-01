package com.teamk.scoretrack.module.core.api.nbaapi.commons.domain;

import com.teamk.scoretrack.module.commons.mongo.domain.Identifier;
import com.teamk.scoretrack.module.core.api.nbaapi.entities.season.domain.SupportedSeasons;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document
public class APINbaUpdate extends Identifier {
    private Instant started;
    private Instant finished;
    private String collectionName;
    private Status status;
    private List<SupportedSeasons> updatedSeasons;

    public APINbaUpdate(Instant started, Instant finished, String collectionName, Status status) {
        this.started = started;
        this.finished = finished;
        this.collectionName = collectionName;
        this.status = status;
        this.updatedSeasons = new ArrayList<>();
    }

    public APINbaUpdate(Instant finished, Status status) {
        this.finished = finished;
        this.status = status;
        this.updatedSeasons = new ArrayList<>();
    }

    public APINbaUpdate() {
        this.updatedSeasons = new ArrayList<>();
    }

    public Instant getStarted() {
        return started;
    }

    public void setStarted(Instant started) {
        this.started = started;
    }

    public Instant getFinished() {
        return finished;
    }

    public void setFinished(Instant finished) {
        this.finished = finished;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<SupportedSeasons> getUpdatedSeasons() {
        return updatedSeasons;
    }

    public void setUpdatedSeasons(List<SupportedSeasons> updatedSeasons) {
        this.updatedSeasons = updatedSeasons;
    }

    public enum Status {
        FINISHED, PROCESSING, WITH_ERRORS;

        public boolean isFinished() {
            return this == FINISHED;
        }

        public boolean isWithErrors() {
            return this == WITH_ERRORS;
        }
    }
}
