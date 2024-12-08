package com.teamk.scoretrack.module.security.session.io;

import com.teamk.scoretrack.module.commons.io.mixin.IMixinAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.CopyOnWriteArrayList;

@Component
@SuppressWarnings("rawtypes")
public class CopyOnWriteArrayListMixinProvider implements IMixinAware<CopyOnWriteArrayList> {
    @Override
    public Class<CopyOnWriteArrayList> getTargetClass() {
        return CopyOnWriteArrayList.class;
    }
}
