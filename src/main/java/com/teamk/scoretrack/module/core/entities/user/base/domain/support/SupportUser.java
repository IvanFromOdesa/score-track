package com.teamk.scoretrack.module.core.entities.user.base.domain.support;

import com.teamk.scoretrack.module.core.entities.user.base.domain.User;
import com.teamk.scoretrack.module.core.entities.user.base.domain.UserGroup;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.Arrays;

@Entity
public class SupportUser extends User {
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public UserGroup getUserGroup() {
        return UserGroup.SUPPORT_USER;
    }

    @Override
    public String[] getPrivileges() {
        return Arrays.stream(role.getPrivileges()).map(p -> formatPrivileges(getUserGroup().getPrivilegeGroup()) + "_" + p).toArray(String[]::new);
    }
}
