package com.teamk.scoretrack.module.security.token.otp.service.form;

import com.teamk.scoretrack.module.commons.form.mvc.AbstractMvcFormOptionsService;
import com.teamk.scoretrack.module.commons.form.mvc.MvcForm;
import com.teamk.scoretrack.module.commons.i18n.service.TranslatorService;
import com.teamk.scoretrack.module.commons.util.CommonsUtil;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.token.otp.service.i18n.OTPTranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OtpFormOptionsService extends AbstractMvcFormOptionsService<MvcForm> {
    @Override
    public void prepareFormOptions(MvcForm mvcForm) {
        Map<String, String> messages = new HashMap<>(translatorService.getMessages());
        String email = ((AuthenticationBean) mvcForm.getAuthentication().getPrincipal()).getEmail();
        messages.put("otpDesc", translatorService.getMessage("otpDesc", CommonsUtil.anonymize(email, 2, email.indexOf("@") - 1)));
        mvcForm.getModel().addAllAttributes(messages);
    }

    @Override
    @Autowired
    protected void setTranslatorService(@Qualifier(OTPTranslatorService.NAME) TranslatorService translatorService) {
        this.translatorService = translatorService;
    }
}
