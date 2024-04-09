package com.teamk.scoretrack.module.core.api.commons.init.service;

import com.teamk.scoretrack.module.commons.form.rest.RestForm;
import com.teamk.scoretrack.module.core.api.commons.init.dto.InitResponse;
import com.teamk.scoretrack.module.core.api.commons.init.service.form.AbstractApiInitFormOptionsService;
import com.teamk.scoretrack.module.core.entities.user.base.domain.UserGroup;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationHolderService;
import com.teamk.scoretrack.module.security.token.jwt.model.AccessToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ApiInitServiceDelegator {
    private final AuthenticationHolderService authenticationHolderService;
    private final Map<UserGroup, AbstractApiInitFormOptionsService> delegateMap;

    public ApiInitServiceDelegator(AuthenticationHolderService authenticationHolderService, List<AbstractApiInitFormOptionsService> apiInitFormOptionsServices) {
        this.authenticationHolderService = authenticationHolderService;
        this.delegateMap = apiInitFormOptionsServices.stream().collect(Collectors.toMap(AbstractApiInitFormOptionsService::getUserGroup, Function.identity()));
    }

    public void prepareFormOptions(RestForm<InitResponse> initResponseRestForm, Consumer<AccessToken> sessionBind) {
        UserGroup userGroup = authenticationHolderService.getUserGroup();
        AbstractApiInitFormOptionsService apiInitFormOptionsService = delegateMap.get(userGroup);
        if (apiInitFormOptionsService == null) {
            throw new IllegalStateException("No mapping for the user group %s".formatted(userGroup));
        } else {
            apiInitFormOptionsService.prepareFormOptions(initResponseRestForm, sessionBind);
        }
    }
}
