package com.teamk.scoretrack.module.security.pwdreset.service.form;

import com.teamk.scoretrack.module.commons.form.mvc.AbstractMvcFormOptionsService;
import com.teamk.scoretrack.module.commons.form.mvc.MvcForm;
import com.teamk.scoretrack.module.commons.i18n.service.TranslatorService;
import com.teamk.scoretrack.module.security.pwdreset.service.i18n.PwdResetTranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PwdResetFormOptionsService extends AbstractMvcFormOptionsService<MvcForm> {
    @Override
    public void prepareFormOptions(MvcForm form) {
        prepareFormOptions(form, "pwdreset");
    }

    public void prepareNewPwdFormOptions(MvcForm form) {
        prepareFormOptions(form, "newpwd");
    }

    private void prepareFormOptions(MvcForm form, String bundle) {
        super.prepareFormOptions(form);
        form.getModel().addAllAttributes(translatorService.getMessages(bundle));
    }

    @Override
    @Autowired
    protected void setTranslatorService(@Qualifier(PwdResetTranslatorService.NAME) TranslatorService translatorService) {
        this.translatorService = translatorService;
    }
}
