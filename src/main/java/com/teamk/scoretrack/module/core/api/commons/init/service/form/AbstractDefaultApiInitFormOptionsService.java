package com.teamk.scoretrack.module.core.api.commons.init.service.form;

import com.teamk.scoretrack.module.commons.form.rest.RestForm;
import com.teamk.scoretrack.module.core.api.commons.init.dto.InitResponse;
import com.teamk.scoretrack.module.core.api.commons.init.dto.UserDataDto;
import com.teamk.scoretrack.module.core.api.commons.sport_components.service.SportComponentMetadataCollectService;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.token.jwt.model.AccessToken;
import com.teamk.scoretrack.module.security.token.jwt.service.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.function.Consumer;

public abstract class AbstractDefaultApiInitFormOptionsService extends AbstractApiInitFormOptionsService {
    @Autowired
    protected AccessTokenService accessTokenService;
    @Autowired
    protected SportComponentMetadataCollectService componentMetadataCollectService;

    public void prepareFormOptions(RestForm<InitResponse> form, Consumer<AccessToken> sessionBind) {
        InitResponse dto = form.getDto();
        AuthenticationBean authenticationBean = (AuthenticationBean) form.getAuthentication().getPrincipal();
        AccessToken token = accessTokenService.generateToken(authenticationBean);
        dto.setUserData(getUserDataDto(authenticationBean, token));
        dto.setBundle(new HashMap<>(apiInitTranslatorService.getMessages(getApiInitBundleName())));
        dto.setApiSportComponentsMetadata(componentMetadataCollectService.getApisSportComponentsMetadata());
        if (sessionBind != null) {
            sessionBind.accept(token);
        }
    }

    protected abstract String getApiInitBundleName();
    protected abstract UserDataDto getUserDataDto(AuthenticationBean authenticationBean, AccessToken token);
}
