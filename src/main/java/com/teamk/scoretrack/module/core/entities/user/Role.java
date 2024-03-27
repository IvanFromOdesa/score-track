package com.teamk.scoretrack.module.core.entities.user;

import com.teamk.scoretrack.module.commons.util.CommonsUtil;

public enum Role {
    DEFAULT(""),
    CLIENT("Client"),
    S_ADMIN("S-Admin", sAdminPrivileges()),
    ADMIN("Admin", adminPrivileges()),
    SUPPORT("Support user", Privileges.USER_ACTIVITIES, Privileges.MODERATOR_PRIVILEGE);

    /**
     * ROLE is treated the same as Granted authority, but prefixed with ROLE_
     */
    private static final String ROLE_PREFIX = "ROLE_";

    private final String alias;
    private final Privileges[] privileges;

    Role(String alias, Privileges... privileges) {
        this.alias = alias;
        this.privileges = privileges;
    }

    public String getAlias() {
        return alias;
    }

    public String getRoleAlias() {
        return ROLE_PREFIX.concat(alias);
    }

    public Privileges[] getPrivileges() {
        return privileges;
    }

    public static Privileges[] sAdminPrivileges() {
        return CommonsUtil.concat2Arrays(adminPrivileges(), new Privileges[] {
                Privileges.API_MANAGEMENT, Privileges.CONTENT_MANAGEMENT, Privileges.APP_MANAGEMENT
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
