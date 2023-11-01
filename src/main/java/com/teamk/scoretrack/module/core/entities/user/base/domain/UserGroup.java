package com.teamk.scoretrack.module.core.entities.user.base.domain;

import com.teamk.scoretrack.module.core.entities.Privileges;

/**
 * Enum holding all possible user groups.
 */
public enum UserGroup {
    DEFAULT(-1, ""),
    FAN(0, Privileges.API_ACCESS),
    CREATOR(1, Privileges.API_ACCESS, Privileges.CONTENT_CREATOR),
    SUPPORT_USER(2, Privileges.SUPPORT_MANAGEMENT);

    private final int code;
    private final String[] privilegeGroup;

    UserGroup(int code, String... privilegeGroup) {
        this.code = code;
        this.privilegeGroup = privilegeGroup;
    }

    public int getCode() {
        return code;
    }

    public String[] getPrivilegeGroup() {
        return privilegeGroup;
    }

    public boolean isSupport() {
        return this == SUPPORT_USER;
    }

    public boolean isBusiness() {
        return this == FAN || this == CREATOR;
    }

    public boolean isFan() {
        return this == FAN;
    }

    public static UserGroup byCode(int code) {
        for (UserGroup userGroup : UserGroup.values()) {
            if (userGroup.code == code) {
                return userGroup;
            }
        }
        return DEFAULT;
    }
}
