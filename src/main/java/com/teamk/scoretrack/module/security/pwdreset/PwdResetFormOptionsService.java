package com.teamk.scoretrack.module.security.pwdreset;

import com.teamk.scoretrack.module.commons.form.mvc.AbstractMvcFormOptionsService;
import com.teamk.scoretrack.module.commons.form.mvc.MvcForm;
import com.teamk.scoretrack.module.commons.i18n.service.TranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PwdResetFormOptionsService extends AbstractMvcFormOptionsService<MvcForm> {
    @Override
    public void prepareFormOptions(MvcForm form) {
        super.prepareFormOptions(form);
        form.getModel().addAllAttributes(translatorService.getMessages());
    }

    @Override
    @Autowired
    protected void setTranslatorService(@Qualifier(PwdResetTranslatorService.NAME) TranslatorService translatorService) {
        this.translatorService = translatorService;
    }
}
