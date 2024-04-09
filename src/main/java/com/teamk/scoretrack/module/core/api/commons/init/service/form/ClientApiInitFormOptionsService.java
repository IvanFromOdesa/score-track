package com.teamk.scoretrack.module.core.api.commons.init.service.form;

import com.teamk.scoretrack.module.core.api.commons.init.dto.UserDataDto;
import com.teamk.scoretrack.module.core.api.commons.init.service.data.ClientUserDataDtoPopulateService;
import com.teamk.scoretrack.module.core.entities.user.base.domain.UserGroup;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ClientUser;
import com.teamk.scoretrack.module.core.entities.user.client.service.ClientUserEntityService;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.token.jwt.model.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientApiInitFormOptionsService extends AbstractDefaultApiInitFormOptionsService {
    private final ClientUserEntityService clientUserEntityService;
    private final ClientUserDataDtoPopulateService dtoPopulateService;

    @Autowired
    public ClientApiInitFormOptionsService(ClientUserEntityService clientUserEntityService, ClientUserDataDtoPopulateService dtoPopulateService) {
        this.clientUserEntityService = clientUserEntityService;
        this.dtoPopulateService = dtoPopulateService;
    }

    @Override
    protected String getApiInitBundleName() {
        return "home";
    }

    @Override
    protected UserDataDto getUserDataDto(AuthenticationBean authenticationBean, AccessToken token) {
        ClientUser user = clientUserEntityService.getByIdOrThrow(authenticationBean.getId());
        return dtoPopulateService.fill(user, token);
    }

    @Override
    public UserGroup getUserGroup() {
        return UserGroup.CLIENT;
    }
}
