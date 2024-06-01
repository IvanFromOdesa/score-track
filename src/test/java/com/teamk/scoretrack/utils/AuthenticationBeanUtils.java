package com.teamk.scoretrack.utils;

import com.teamk.scoretrack.module.core.entities.user.base.domain.Language;
import com.teamk.scoretrack.module.core.entities.user.base.domain.User;
import com.teamk.scoretrack.module.core.entities.user.base.domain.UserPrivilege;
import com.teamk.scoretrack.module.core.entities.user.client.domain.ClientUser;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationStatus;
import com.teamk.scoretrack.module.security.auth.domain.PasswordStatus;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public final class AuthenticationBeanUtils {
    public static final List<UserPrivilege> AUTHORITIES = List.of(new UserPrivilege("Authority"));
    public static final UUID DEFAULT_EXTERNAL_ID = UUID.randomUUID();
    public static final String DEFAULT_LOGINNNAME = "Loginname";
    public static final String NOT_ACTIVATED_LOGINNAME = "newUser";
    public static final String DEFAULT_PASSWORD = "12345678";
    public static final String DEFAULT_EMAIL = "login@email.com";
    public static final Language DEFAULT_PREFERRED_LANGUAGE = Language.UKRAINIAN;

    public static AuthenticationBean mockAuthenticationBean() {
        return mockAuthenticationBean(DEFAULT_LOGINNNAME, DEFAULT_PASSWORD, AuthenticationStatus.ACTIVATED);
    }

    public static AuthenticationBean mockNotActivatedAuthenticationBean() {
        return mockAuthenticationBean(NOT_ACTIVATED_LOGINNAME, DEFAULT_PASSWORD, AuthenticationStatus.CREATED);
    }

    public static AuthenticationBean mockAuthenticationBean(String loginname, String password, AuthenticationStatus authenticationStatus) {
        AuthenticationBean authenticationBean = new AuthenticationBean() {
            @Override
            public Collection<UserPrivilege> getAuthorities() {
                return AUTHORITIES;
            }
        };
        authenticationBean.setExternalId(DEFAULT_EXTERNAL_ID);
        authenticationBean.setLoginname(loginname);
        authenticationBean.setEmail(DEFAULT_EMAIL);
        authenticationBean.setPassword(password);
        authenticationBean.setStatus(authenticationStatus);
        authenticationBean.setPs(PasswordStatus.NEW);
        authenticationBean.setUser(mockUser());
        return authenticationBean;
    }


    private static User mockUser() {
        User user = new ClientUser();
        user.setPreferredLang(DEFAULT_PREFERRED_LANGUAGE);
        return user;
    }

    private AuthenticationBeanUtils() {

    }
}
