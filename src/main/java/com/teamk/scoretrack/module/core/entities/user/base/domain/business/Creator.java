package com.teamk.scoretrack.module.core.entities.user.base.domain.business;

import com.teamk.scoretrack.module.core.entities.user.base.domain.UserGroup;
import jakarta.persistence.Entity;

// TODO: move to separate sub-module
@Entity
public class Creator extends BusinessUser {
    @Override
    public UserGroup getUserGroup() {
        return UserGroup.CREATOR;
    }
}
