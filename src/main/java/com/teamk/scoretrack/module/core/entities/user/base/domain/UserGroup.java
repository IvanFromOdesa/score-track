package com.teamk.scoretrack.module.core.entities.user.base.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static com.teamk.scoretrack.module.core.entities.user.base.domain.Role.*;

/**
 * Enum holding all possible user groups.
 * Roles are grouped under specific user group.
 */
public enum UserGroup {
    ANONYMOUS(-1, List.of(Role.ANONYMOUS)),
    CLIENT(3, List.of(FAN, CREATOR), Privileges.API_ACCESS),
    SUPPORT(2, List.of(MOD, ADMIN, S_ADMIN), Privileges.SUPPORT_MANAGEMENT);

    private static final Map<Integer, UserGroup> LOOKUP_MAP = new HashMap<>(3);

    static {
        LOOKUP_MAP.put(ANONYMOUS.code, ANONYMOUS);
        LOOKUP_MAP.put(CLIENT.code, CLIENT);
        LOOKUP_MAP.put(SUPPORT.code, SUPPORT);
    }

    private final int code;
    private final List<Role> roles;
    /**
     * All the roles under specific user group inherit this privilege.
     */
    private final Privileges[] privilegeGroup;

    UserGroup(int code, List<Role> roles, Privileges... privilegeGroup) {
        this.code = code;
        this.roles = roles;
        this.privilegeGroup = privilegeGroup;
    }

    public int getCode() {
        return code;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public Privileges[] getPrivilegeGroup() {
        return privilegeGroup;
    }

    public boolean isSupport() {
        return this == SUPPORT;
    }

    public boolean isClient() {
        return this == CLIENT;
    }

    public static UserGroup byRole(Role role) {
        return getUserGroup(v -> v.roles.contains(role));
    }

    public static UserGroup byRoleAlias(String roleAlias) {
        return getUserGroup(v -> v.roles.stream().anyMatch(r -> r.getRoleAlias().equals(roleAlias)));
    }

    private static UserGroup getUserGroup(Predicate<UserGroup> byRole) {
        return LOOKUP_MAP.values().stream().filter(byRole).findFirst().orElse(ANONYMOUS);
    }
}
