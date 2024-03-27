package com.teamk.scoretrack.module.core.entities.user.base.domain;

import com.google.common.base.Objects;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;

public class UserPrivilege implements GrantedAuthority {
    @Serial
    private static final long serialVersionUID = -9097842433655502784L;
    private final String authority;
    private final int[] subAuthorities;

    public UserPrivilege(String authority) {
        this.authority = authority;
        this.subAuthorities = null;
    }

    public UserPrivilege(String authority, int[] subAuthorities) {
        this.authority = authority;
        this.subAuthorities = subAuthorities;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public int[] getSubAuthorities() {
        return subAuthorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrivilege that = (UserPrivilege) o;
        return Objects.equal(authority, that.authority) && Objects.equal(subAuthorities, that.subAuthorities);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(authority, subAuthorities);
    }
}
