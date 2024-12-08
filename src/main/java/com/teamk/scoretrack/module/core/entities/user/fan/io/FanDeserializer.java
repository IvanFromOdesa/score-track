package com.teamk.scoretrack.module.core.entities.user.fan.io;

import com.teamk.scoretrack.module.core.entities.user.client.io.ClientUserDeserializer;
import com.teamk.scoretrack.module.core.entities.user.fan.domain.Fan;
import org.springframework.stereotype.Component;

@Component
public class FanDeserializer extends ClientUserDeserializer<Fan> {
    @Override
    protected Fan createInstance() {
        return new Fan();
    }

    @Override
    public Class<Fan> getDeserializationClass() {
        return Fan.class;
    }
}
