package com.teamk.scoretrack.module.core.entities;

import com.teamk.scoretrack.module.core.entities.user.base.domain.User;
import com.teamk.scoretrack.module.core.entities.user.base.domain.UserPrivilege;

import java.util.Objects;
import java.util.function.Function;

/**
 * @see com.teamk.scoretrack.module.security.acl.AclService
 * @see UserPrivilege
 */
public final class Privileges {
    public static final Privileges API_ACCESS = new Privileges("API_ACCESS", user -> new UserPrivilege("API_ACCESS", user.getAvailableApiCodes()));
    public static final Privileges PROFILE_ACCESS = new Privileges("PROFILE_ACCESS");
    public static final Privileges CONTENT_CREATOR = new Privileges("CONTENT_CREATOR");
    public static final Privileges SUPPORT_MANAGEMENT = new Privileges("SUPPORT_MANAGEMENT");
    public static final Privileges USER_MANAGEMENT = new Privileges("USER_MANAGEMENT");
    public static final Privileges MODERATOR_PRIVILEGE = new Privileges("MODERATOR_FUNC");
    public static final Privileges USER_ACTIVITIES = new Privileges("USER_ACTIVITIES");
    public static final Privileges API_MANAGEMENT = new Privileges("API_MANAGEMENT");
    public static final Privileges CONTENT_MANAGEMENT = new Privileges("CONTENT_MANAGEMENT");
    public static final int ALL_SUBS = 9999;

    private final String privilege;
    private final Function<User, UserPrivilege> privilegeCallback;

    public Privileges(String privilege, Function<User, UserPrivilege> privilegeCallback) {
        this.privilege = privilege;
        this.privilegeCallback = privilegeCallback;
    }

    public Privileges(String privilege) {
        this.privilege = privilege;
        this.privilegeCallback = user -> new UserPrivilege(privilege);
    }

    public String privilege() {
        return privilege;
    }

    public Function<User, UserPrivilege> privilegeCallback() {
        return privilegeCallback;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Privileges) obj;
        return Objects.equals(this.privilege, that.privilege) &&
                Objects.equals(this.privilegeCallback, that.privilegeCallback);
    }

    @Override
    public int hashCode() {
        return Objects.hash(privilege, privilegeCallback);
    }

    @Override
    public String toString() {
        return "Privileges[" +
                "privilege=" + privilege + ", " +
                "privilegeCallback=" + privilegeCallback + ']';
    }

}
