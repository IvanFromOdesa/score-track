package com.teamk.scoretrack.module.core.entities.user.base.domain;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public interface IUserAware {
    default UserGroup getUserGroup() {
        return UserGroup.byRole(getRole());
    }

    Role getRole();

    default List<UserPrivilege> getPrivileges() {
        return getUserPrivilegeStream().toList();
    }

    @NotNull
    default Stream<UserPrivilege> getUserPrivilegeStream() {
        final Stream<UserPrivilege> userPrivilegeStream = Arrays.stream(getUserGroup().getPrivilegeGroup()).map(p -> p.privilegeCallback().apply((User) this));
        return Stream.concat(userPrivilegeStream, Stream.of(new UserPrivilege(getRole().getRoleAlias())));
    }

    default UserGroup getAnonymous() {
        return UserGroup.ANONYMOUS;
    }
}
