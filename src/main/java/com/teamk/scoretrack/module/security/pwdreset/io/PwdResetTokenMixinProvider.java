package com.teamk.scoretrack.module.security.pwdreset.io;

import com.teamk.scoretrack.module.commons.io.mixin.IMixinAware;
import com.teamk.scoretrack.module.security.pwdreset.domain.PwdResetToken;
import org.springframework.stereotype.Component;

@Component
public class PwdResetTokenMixinProvider implements IMixinAware<PwdResetToken> {
    @Override
    public Class<PwdResetToken> getTargetClass() {
        return PwdResetToken.class;
    }
}
