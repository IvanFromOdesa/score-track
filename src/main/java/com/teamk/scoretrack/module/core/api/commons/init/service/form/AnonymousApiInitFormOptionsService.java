package com.teamk.scoretrack.module.core.api.commons.init.service.form;

import com.teamk.scoretrack.module.commons.form.rest.RestForm;
import com.teamk.scoretrack.module.core.api.commons.init.dto.InitResponse;
import com.teamk.scoretrack.module.core.entities.user.base.domain.UserGroup;
import com.teamk.scoretrack.module.security.token.jwt.model.AccessToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.function.Consumer;

@Service
public class AnonymousApiInitFormOptionsService extends AbstractApiInitFormOptionsService {
    @Override
    public void prepareFormOptions(RestForm<InitResponse> form, Consumer<AccessToken> sessionBind) {
        form.getDto().setBundle(new HashMap<>(apiInitTranslatorService.getMessages("ad")));
    }

    @Override
    public UserGroup getUserGroup() {
        return UserGroup.ANONYMOUS;
    }
}
