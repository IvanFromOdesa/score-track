package com.teamk.scoretrack.module.core.entities.user.base.domain;

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
}
