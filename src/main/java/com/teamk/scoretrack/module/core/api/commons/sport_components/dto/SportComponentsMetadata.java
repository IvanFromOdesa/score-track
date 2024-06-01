package com.teamk.scoretrack.module.core.api.commons.sport_components.dto;

import com.teamk.scoretrack.module.core.api.commons.base.BundleResponse;

import java.util.Map;
import java.util.Objects;

public class SportComponentsMetadata {
    private final Map<String, SportComponentMetadata> components;
    private final Map<String, BundleResponse> helpText;

    /**
     * @param components maps component name to its metadata
     * @param helpText help text for sport components
     */
    public SportComponentsMetadata(
            Map<String, SportComponentMetadata> components,
            Map<String, BundleResponse> helpText
    ) {
        this.components = components;
        this.helpText = helpText;
    }

    public Map<String, SportComponentMetadata> getComponents() {
        return components;
    }

    public Map<String, BundleResponse> getHelpText() {
        return helpText;
    }
}
