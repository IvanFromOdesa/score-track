package com.teamk.scoretrack.module.core.entities.user.creator;

import com.teamk.scoretrack.module.core.entities.user.base.domain.Role;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ClientUser;
import jakarta.persistence.Entity;

@Entity
public class Creator extends ClientUser {
    @Override
    public Role getRole() {
        return Role.CREATOR;
    }
}
