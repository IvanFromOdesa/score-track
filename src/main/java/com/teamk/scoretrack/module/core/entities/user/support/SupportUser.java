package com.teamk.scoretrack.module.core.entities.user.support;

import com.teamk.scoretrack.module.core.entities.user.base.domain.User;
import com.teamk.scoretrack.module.core.entities.user.base.domain.UserGroup;
import com.teamk.scoretrack.module.core.entities.user.base.domain.UserPrivilege;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.Arrays;
import java.util.List;

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
    public List<UserPrivilege> getPrivileges() {
        final List<UserPrivilege> privileges = super.getPrivileges();
        privileges.addAll(Arrays.stream(role.getPrivileges()).map(p -> p.privilegeCallback().apply(this)).toList());
        return privileges;
    }
}
