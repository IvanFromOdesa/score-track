package com.teamk.scoretrack.module.core.api.commons.sport_components.service;

import com.teamk.scoretrack.module.core.api.commons.sport_components.dto.ApiSportComponentsMetadata;
import com.teamk.scoretrack.module.core.entities.user.base.domain.Privileges;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SportComponentMetadataCollectService {
    private final AuthenticationHolderService authenticationHolderService;
    private final List<ISportComponentMetadataService> services;

    @Autowired
    public SportComponentMetadataCollectService(AuthenticationHolderService authenticationHolderService, List<ISportComponentMetadataService> services) {
        this.authenticationHolderService = authenticationHolderService;
        this.services = services;
    }

    /**
     * Collects all available apis sport components metadata for the current user.
     * @return all available apis sport components metadata for the current user.
     */
    public ApiSportComponentsMetadata getApisSportComponentsMetadata() {
        return new ApiSportComponentsMetadata(services.stream().filter(this::isPermitted).collect(Collectors.toMap(ISportComponentMetadataService::getApiCode, ISportComponentMetadataService::collect)));
    }

    private boolean isPermitted(ISportComponentMetadataService e) {
        List<Integer> apiCodes = authenticationHolderService.getApiCodesAsList();
        return apiCodes.contains(e.getApiCode()) || apiCodes.contains(Privileges.ALL_SUBS);
    }
}
