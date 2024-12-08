package com.teamk.scoretrack.module.core.entities.user.support.domain;

import com.teamk.scoretrack.module.core.entities.user.base.domain.Role;
import com.teamk.scoretrack.module.core.entities.user.base.domain.User;
import com.teamk.scoretrack.module.core.entities.user.base.domain.UserPrivilege;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.hibernate.annotations.Check;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Entity
@Check(constraints = "role IN ('S_ADMIN', 'ADMIN', 'MOD')")
public class SupportUser extends User {
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Override
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public List<UserPrivilege> getPrivileges() {
        return Stream.concat(getUserPrivilegeStream(), Arrays.stream(role.getPrivileges()).map(p -> p.privilegeCallback().apply(this))).toList();
    }
}
