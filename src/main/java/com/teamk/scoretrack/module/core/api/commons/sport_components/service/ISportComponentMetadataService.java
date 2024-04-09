package com.teamk.scoretrack.module.core.api.commons.sport_components.service;

import com.teamk.scoretrack.module.core.api.commons.sport_components.dto.SportComponentsMetadata;

/**
 * Collects metadata from API providers.
 */
public interface ISportComponentMetadataService {
    SportComponentsMetadata collect();
    int getApiCode();
}
