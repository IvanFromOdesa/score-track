package com.teamk.scoretrack.module.security.token.jwt.io;

import com.teamk.scoretrack.module.commons.io.mixin.IMixinAware;
import com.teamk.scoretrack.module.security.token.jwt.model.AccessToken;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenMixinProvider implements IMixinAware<AccessToken> {
    @Override
    public Class<AccessToken> getTargetClass() {
        return AccessToken.class;
    }
}
