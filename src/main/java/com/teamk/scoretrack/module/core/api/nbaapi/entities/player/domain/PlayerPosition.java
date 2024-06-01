package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain;

/**
 * Describes which positions specific player plays.
 */
public enum PlayerPosition {
    GUARD("G"),
    FORWARD("F"),
    CENTER("C"),
    GUARD_FORWARD("G-F"),
    FORWARD_GUARD("F-G"),
    FORWARD_CENTER("F-C"),
    CENTER_FORWARD("C-F"),
    UNKNOWN("");

    private final String id;

    PlayerPosition(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static PlayerPosition byId(String id) {
        for (PlayerPosition position : PlayerPosition.values()) {
            if (position.id.equals(id)) {
                return position;
            }
        }
        return UNKNOWN;
    }
}
