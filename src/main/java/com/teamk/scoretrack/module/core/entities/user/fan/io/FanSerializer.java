package com.teamk.scoretrack.module.core.entities.user.fan.io;

import com.teamk.scoretrack.module.core.entities.user.client.io.ClientUserSerializer;
import com.teamk.scoretrack.module.core.entities.user.fan.domain.Fan;
import org.springframework.stereotype.Component;

@Component
public class FanSerializer extends ClientUserSerializer<Fan> {
    public FanSerializer() {
        super(Fan.class);
    }

    @Override
    public Class<Fan> getSerializationClass() {
        return Fan.class;
    }
}
