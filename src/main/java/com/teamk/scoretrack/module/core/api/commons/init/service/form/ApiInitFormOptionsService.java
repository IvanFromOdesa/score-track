package com.teamk.scoretrack.module.core.api.commons.init.service.form;

import com.teamk.scoretrack.module.commons.form.rest.AbstractRestFormOptionsService;
import com.teamk.scoretrack.module.commons.form.rest.RestForm;
import com.teamk.scoretrack.module.core.api.commons.base.service.UserDataDtoPopulateService;
import com.teamk.scoretrack.module.core.api.commons.init.dto.InitResponse;
import com.teamk.scoretrack.module.core.api.commons.init.service.i18n.ApiInitTranslatorService;
import com.teamk.scoretrack.module.core.entities.user.base.domain.User;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ClientUser;
import com.teamk.scoretrack.module.core.entities.user.client.service.ClientUserEntityService;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationHolderService;
import com.teamk.scoretrack.module.security.token.jwt.model.AccessToken;
import com.teamk.scoretrack.module.security.token.jwt.service.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class ApiInitFormOptionsService extends AbstractRestFormOptionsService<InitResponse, RestForm<InitResponse>> {
    private final AuthenticationHolderService authenticationHolderService;
    private final ApiInitTranslatorService apiInitTranslatorService;
    private final ClientUserEntityService clientUserEntityService;
    private final UserDataDtoPopulateService dtoPopulateService;
    private final AccessTokenService accessTokenService;

    @Autowired
    public ApiInitFormOptionsService(AuthenticationHolderService authenticationHolderService,
                                     ApiInitTranslatorService apiInitTranslatorService,
                                     ClientUserEntityService clientUserEntityService,
                                     UserDataDtoPopulateService dtoPopulateService,
                                     AccessTokenService accessTokenService) {
        this.authenticationHolderService = authenticationHolderService;
        this.apiInitTranslatorService = apiInitTranslatorService;
        this.clientUserEntityService = clientUserEntityService;
        this.dtoPopulateService = dtoPopulateService;
        this.accessTokenService = accessTokenService;
    }

    public void prepareFormOptions(RestForm<InitResponse> initResponseRestForm, Consumer<AccessToken> sessionBind) {
        Optional<AuthenticationBean> currentAuthentication = authenticationHolderService.getCurrentAuthentication();
        InitResponse dto = initResponseRestForm.getDto();
        if (currentAuthentication.isEmpty()) {
            dto.setBundle(new HashMap<>(apiInitTranslatorService.getMessages("ad")));
        } else {
            AuthenticationBean authenticationBean = currentAuthentication.get();
            User user = authenticationBean.getUser();
            if (user instanceof ClientUser) {
                user = clientUserEntityService.getByIdOrThrow(user.getId());
                authenticationBean.setUser(user);
            }
            dto.setBundle(new HashMap<>(apiInitTranslatorService.getMessages("home")));
            AccessToken token = accessTokenService.generateToken(authenticationBean);
            dto.setUserData(dtoPopulateService.fill(user, token));
            if (sessionBind != null) {
                sessionBind.accept(token);
            }
        }
    }

    @Override
    public void prepareFormOptions(RestForm<InitResponse> initResponseRestForm) {
        prepareFormOptions(initResponseRestForm, null);
    }
}
