package com.teamk.scoretrack.module.core.entities.sport_type;

/**
 * This is used to localize SportType name text.
 * @param code {@link SportType#getKey()}
 * @param name localized name
 */
public record SportTypeDto(SportType code, String name) {
}
