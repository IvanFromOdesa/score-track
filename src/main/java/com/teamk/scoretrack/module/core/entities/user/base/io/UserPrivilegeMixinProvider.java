package com.teamk.scoretrack.module.core.entities.user.base.io;

import com.teamk.scoretrack.module.commons.io.mixin.IMixinAware;
import com.teamk.scoretrack.module.core.entities.user.base.domain.UserPrivilege;
import org.springframework.stereotype.Component;

@Component
public class UserPrivilegeMixinProvider implements IMixinAware<UserPrivilege> {
    @Override
    public Class<UserPrivilege> getTargetClass() {
        return UserPrivilege.class;
    }
}
