package com.teamk.scoretrack.module.core.api.nbaapi.commons.domain;

import com.teamk.scoretrack.module.commons.mongo.domain.Identifier;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
public class APINbaUpdate extends Identifier {
    private Instant started;
    private Instant finished;
    private String collectionName;
    private Status status;

    public APINbaUpdate(Instant started, Instant finished, String collectionName, Status status) {
        this.started = started;
        this.finished = finished;
        this.collectionName = collectionName;
        this.status = status;
    }

    public APINbaUpdate(Instant finished, Status status) {
        this.finished = finished;
        this.status = status;
    }

    public APINbaUpdate() {
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
