package com.teamk.scoretrack.module.security.auth.service.form;

import com.teamk.scoretrack.module.commons.form.mvc.AbstractMvcFormOptionsService;
import com.teamk.scoretrack.module.commons.form.mvc.MvcForm;
import com.teamk.scoretrack.module.commons.i18n.service.TranslatorService;
import com.teamk.scoretrack.module.security.auth.service.i18n.AuthTranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AuthFormOptionsService extends AbstractMvcFormOptionsService<MvcForm> {
    @Override
    public void prepareFormOptions(MvcForm mvcForm) {
        super.prepareFormOptions(mvcForm);
        mvcForm.getModel().addAllAttributes(translatorService.getMessages(mvcForm.getBundleName()));
    }

    @Override
    @Autowired
    protected void setTranslatorService(@Qualifier(AuthTranslatorService.NAME) TranslatorService translatorService) {
        this.translatorService = translatorService;
    }
}
