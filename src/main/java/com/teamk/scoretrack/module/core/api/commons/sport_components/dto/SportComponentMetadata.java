package com.teamk.scoretrack.module.core.api.commons.sport_components.dto;

import java.time.Instant;

/**
 * Contains info about current api's components metadata: last updated, accessible etc.
 * Used for UI rendering. Examples: 'Games', 'Teams', 'Players'.
 */
public class SportComponentMetadata {
    private String name;
    private Instant updated;
    private Status status;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }

    public enum Status {
        ACCESSIBLE, DOWN;

        public boolean isAccessible() {
            return this == ACCESSIBLE;
        }
    }
}
