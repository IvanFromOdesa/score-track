package com.teamk.scoretrack.module.core.entities.user.base.domain;

import java.util.Arrays;

public interface IUserAware {
    UserGroup getUserGroup();

    default String[] getPrivileges() {
        return getUserGroup().getPrivilegeGroup();
    }

    default String formatPrivileges(String... privileges) {
        return Arrays.toString(privileges)
                .replace("[", "")
                .replace("]", "")
                .replace(",", "_");
    }

    default UserGroup getAnonymous() {
        return UserGroup.DEFAULT;
    }
}
