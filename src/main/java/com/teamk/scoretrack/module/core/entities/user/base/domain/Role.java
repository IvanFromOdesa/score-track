package com.teamk.scoretrack.module.core.entities.user.base.domain;

import com.teamk.scoretrack.module.commons.util.CommonsUtil;

import java.util.Arrays;

public enum Role {
    ANONYMOUS(""),
    FAN("Fan"),
    CREATOR("Creator", Privileges.CONTENT_CREATOR),
    S_ADMIN("S-Admin", sAdminPrivileges()),
    ADMIN("Admin", adminPrivileges()),
    MOD("Moderator", Privileges.USER_ACTIVITIES, Privileges.MODERATOR_PRIVILEGE);

    /**
     * ROLE is treated the same as Granted authority, but prefixed with ROLE_
     */
    public static final String ROLE_PREFIX = "ROLE_";

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

    public static boolean isRoleAlias(String s) {
        return s.startsWith(ROLE_PREFIX);
    }

    public Privileges[] getPrivileges() {
        return privileges;
    }

    public boolean containsPrivilege(Privileges privilege) {
        return Arrays.asList(privileges).contains(privilege);
    }

    public static Role byRoleAlias(String roleAlias) {
        return Arrays.stream(Role.values()).filter(r -> r.getRoleAlias().equals(roleAlias)).findAny().orElseThrow();
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
