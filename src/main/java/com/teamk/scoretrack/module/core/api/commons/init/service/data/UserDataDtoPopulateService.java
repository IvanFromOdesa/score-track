package com.teamk.scoretrack.module.core.api.commons.init.service.data;

import com.teamk.scoretrack.module.core.api.commons.init.dto.UserDataDto;
import com.teamk.scoretrack.module.core.entities.user.base.domain.User;
import com.teamk.scoretrack.module.security.recaptcha.RecaptchaKeyProperties;
import com.teamk.scoretrack.module.security.token.jwt.model.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;

public abstract class UserDataDtoPopulateService<USER_T extends User> {
    @Autowired
    private RecaptchaKeyProperties recaptchaKeyProperties;

    public abstract UserDataDto fill(USER_T user, AccessToken accessToken);

    protected void setCommonProps(AccessToken token, UserDataDto dto) {
        dto.setToken(token);
        dto.setRecaptchaKey(recaptchaKeyProperties.publicKey());
        dto.setLocale(LocaleContextHolder.getLocale().getLanguage());
    }
}
