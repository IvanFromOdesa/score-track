package com.teamk.scoretrack.module.core.entities.user.base.domain.support;

import com.teamk.scoretrack.module.commons.util.CommonsUtil;
import com.teamk.scoretrack.module.core.entities.Privileges;

public enum Role {
    S_ADMIN("S-Admin", sAdminPrivileges()),
    ADMIN("Admin", adminPrivileges()),
    BASE("Support user", Privileges.USER_ACTIVITIES, Privileges.MODERATOR_PRIVILEGE);

    private final String alias;
    private final String[] privileges;

    Role(String alias, String... privileges) {
        this.alias = alias;
        this.privileges = privileges;
    }

    public String getAlias() {
        return alias;
    }

    public String[] getPrivileges() {
        return privileges;
    }

    public static String[] sAdminPrivileges() {
        return CommonsUtil.concat2Arrays(adminPrivileges(), new String[] {
                Privileges.API_MANAGEMENT, Privileges.CONTENT_MANAGEMENT
        });
    }

    public static String[] adminPrivileges() {
        return new String[] {
                Privileges.USER_MANAGEMENT,
                Privileges.USER_ACTIVITIES,
                Privileges.MODERATOR_PRIVILEGE,
                Privileges.API_ACCESS
        };
    }
}
