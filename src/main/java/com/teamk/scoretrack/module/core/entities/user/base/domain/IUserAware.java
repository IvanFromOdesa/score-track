package com.teamk.scoretrack.module.core.entities.user.base.domain;

import java.util.Arrays;
import java.util.List;

public interface IUserAware {
    UserGroup getUserGroup();

    default List<UserPrivilege> getPrivileges() {
        return Arrays.stream(getUserGroup().getPrivilegeGroup()).map(p -> p.privilegeCallback().apply((User) this)).toList();
    }

    default UserGroup getAnonymous() {
        return UserGroup.DEFAULT;
    }
}
