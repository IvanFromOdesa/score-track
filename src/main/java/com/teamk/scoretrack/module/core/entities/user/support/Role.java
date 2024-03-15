package com.teamk.scoretrack.module.core.entities.user.support;

import com.teamk.scoretrack.module.commons.util.CommonsUtil;
import com.teamk.scoretrack.module.core.entities.Privileges;

public enum Role {
    S_ADMIN("S-Admin", sAdminPrivileges()),
    ADMIN("Admin", adminPrivileges()),
    BASE("Support user", Privileges.USER_ACTIVITIES, Privileges.MODERATOR_PRIVILEGE);

    private final String alias;
    private final Privileges[] privileges;

    Role(String alias, Privileges... privileges) {
        this.alias = alias;
        this.privileges = privileges;
    }

    public String getAlias() {
        return alias;
    }

    public Privileges[] getPrivileges() {
        return privileges;
    }

    public static Privileges[] sAdminPrivileges() {
        return CommonsUtil.concat2Arrays(adminPrivileges(), new Privileges[] {
                Privileges.API_MANAGEMENT, Privileges.CONTENT_MANAGEMENT
        });
    }

    public static Privileges[] adminPrivileges() {
        return new Privileges[] {
                Privileges.USER_MANAGEMENT,
                Privileges.USER_ACTIVITIES,
                Privileges.MODERATOR_PRIVILEGE,
                Privileges.API_ACCESS
        };
    }
}
