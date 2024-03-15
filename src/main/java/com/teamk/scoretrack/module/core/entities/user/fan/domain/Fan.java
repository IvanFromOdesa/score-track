package com.teamk.scoretrack.module.core.entities.user.fan.domain;

import com.teamk.scoretrack.module.core.entities.user.base.domain.UserGroup;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ClientUser;
import jakarta.persistence.Table;

@jakarta.persistence.Entity
@Table(name = "fan")
public class Fan extends ClientUser {
    @Override
    public UserGroup getUserGroup() {
        return UserGroup.FAN;
    }
}
