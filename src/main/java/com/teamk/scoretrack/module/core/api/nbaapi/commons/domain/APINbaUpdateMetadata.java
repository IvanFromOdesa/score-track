package com.teamk.scoretrack.module.core.api.nbaapi.commons.domain;

import com.teamk.scoretrack.module.commons.base.domain.IdAware;

import java.time.Instant;

public class APINbaUpdateMetadata implements IdAware<String> {
    private String name;
    private Instant updated;
    private APINbaUpdate.Status status;
    private int updateCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public APINbaUpdate.Status getStatus() {
        return status;
    }

    public void setStatus(APINbaUpdate.Status status) {
        this.status = status;
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }

    @Override
    public String getId() {
        return name;
    }
}