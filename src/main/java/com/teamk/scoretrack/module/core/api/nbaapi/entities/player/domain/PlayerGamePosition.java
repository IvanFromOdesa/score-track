package com.teamk.scoretrack.module.core.api.nbaapi.entities.player.domain;

/**
 * Describes concrete player position in a concrete game.
 */
public enum PlayerGamePosition {
    POINT_GUARD("PG"),
    SHOOTING_GUARD("SG"),
    SMALL_FORWARD("SF"),
    POWER_FORWARD("PF"),
    CENTER("C"),
    UNKNOWN("");

    private final String id;

    PlayerGamePosition(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static PlayerGamePosition byId(String id) {
        for (PlayerGamePosition position: PlayerGamePosition.values()) {
            if (position.id.equals(id)) {
                return position;
            }
        }
        return UNKNOWN;
    }
}
