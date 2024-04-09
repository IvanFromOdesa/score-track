package com.teamk.scoretrack.module.core.api.commons.sport_components.dto;

import java.util.Map;

public record SportComponentsMetadata(
        /**
         * Maps component name to its metadata
         */
        Map<String, SportComponentMetadata> components
) {
}
