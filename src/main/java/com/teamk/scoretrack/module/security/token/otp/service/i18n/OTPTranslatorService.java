package com.teamk.scoretrack.module.security.token.otp.service.i18n;

import com.teamk.scoretrack.module.commons.i18n.config.LocaleConfiguration;
import com.teamk.scoretrack.module.commons.i18n.service.TranslatorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service(OTPTranslatorService.NAME)
public class OTPTranslatorService extends TranslatorService {
    public static final String NAME = "otpTranslatorService";

    @Override
    public void setMessageSource(@Qualifier(LocaleConfiguration.OTP) MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected String provideBaseBundleBeanName() {
        return LocaleConfiguration.OTP;
    }
}
