package com.teamk.scoretrack.module.core.api.nbaapi.commons.dto;

import com.teamk.scoretrack.module.core.api.commons.base.BundleResponse;
import com.teamk.scoretrack.module.core.api.commons.sport_components.dto.SportComponentMetadata;
import com.teamk.scoretrack.module.core.api.commons.sport_components.dto.SportComponentsMetadata;

import java.util.Map;

public class APINbaSportComponentsMetadata extends SportComponentsMetadata {
    private final Map<String, APINbaHelpData> helpData;
    /**
     * @param components maps component name to its metadata
     * @param helpText   help text for sport components
     * @param helpData additional info for sport components
     */
    public APINbaSportComponentsMetadata(Map<String, SportComponentMetadata> components,
                                         Map<String, BundleResponse> helpText,
                                         Map<String, APINbaHelpData> helpData) {
        super(components, helpText);
        this.helpData = helpData;
    }

    public Map<String, APINbaHelpData> getHelpData() {
        return helpData;
    }
}
