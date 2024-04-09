package com.teamk.scoretrack.module.core.api.commons.init.service.form;

import com.teamk.scoretrack.module.commons.form.rest.AbstractRestFormOptionsService;
import com.teamk.scoretrack.module.commons.form.rest.RestForm;
import com.teamk.scoretrack.module.core.api.commons.init.dto.InitResponse;
import com.teamk.scoretrack.module.core.api.commons.init.service.i18n.ApiInitTranslatorService;
import com.teamk.scoretrack.module.core.entities.user.base.domain.UserGroup;
import com.teamk.scoretrack.module.security.token.jwt.model.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Consumer;

public abstract class AbstractApiInitFormOptionsService extends AbstractRestFormOptionsService<InitResponse, RestForm<InitResponse>> {
    @Autowired
    protected ApiInitTranslatorService apiInitTranslatorService;

    public abstract void prepareFormOptions(RestForm<InitResponse> form, Consumer<AccessToken> sessionBind);
    public abstract UserGroup getUserGroup();

    @Override
    public void prepareFormOptions(RestForm<InitResponse> initResponseRestForm) {
        prepareFormOptions(initResponseRestForm, null);
    }
}
