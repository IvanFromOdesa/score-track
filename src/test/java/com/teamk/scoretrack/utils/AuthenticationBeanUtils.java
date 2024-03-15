package com.teamk.scoretrack.utils;

import com.teamk.scoretrack.module.core.entities.user.base.domain.Language;
import com.teamk.scoretrack.module.core.entities.user.base.domain.User;
import com.teamk.scoretrack.module.core.entities.user.base.domain.UserPrivilege;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public final class AuthenticationBeanUtils {
    public static final List<UserPrivilege> AUTHORITIES = List.of(new UserPrivilege("Authority"));
    public static final UUID EXTERNAL_ID = UUID.randomUUID();
    public static final String LOGINNNAME = "Loginname";
    public static final String EMAIL = "login@email.com";
    public static final Language PREFERRED_LANGUAGE = Language.UKRAINIAN;

    public static AuthenticationBean mockAuthenticationBean() {
        AuthenticationBean authenticationBean = new AuthenticationBean() {
            @Override
            public Collection<UserPrivilege> getAuthorities() {
                return AUTHORITIES;
            }
        };
        authenticationBean.setExternalId(EXTERNAL_ID);
        authenticationBean.setLoginname(LOGINNNAME);
        authenticationBean.setEmail(EMAIL);
        authenticationBean.setUser(mockUser());
        return authenticationBean;
    }

    private static User mockUser() {
        User user = new User();
        user.setPreferredLang(PREFERRED_LANGUAGE);
        return user;
    }

    private AuthenticationBeanUtils() {

    }
}
