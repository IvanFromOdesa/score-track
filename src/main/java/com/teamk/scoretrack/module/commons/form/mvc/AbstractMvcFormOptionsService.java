package com.teamk.scoretrack.module.commons.form.mvc;

import com.teamk.scoretrack.module.commons.form.IFormOptionsService;
import com.teamk.scoretrack.module.commons.i18n.service.TranslatorService;
import com.teamk.scoretrack.module.commons.layout.navmenu.NavMenuOptionsPreparer;
import com.teamk.scoretrack.module.security.recaptcha.RecaptchaKeyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Child classes must provide an implementation for {@link AbstractMvcFormOptionsService#prepareFormOptions(MvcForm)}
 * which is used to collect all necessary options for the form getModel. This includes texts from bundles,
 * lists of options, separate objects etc.
 * @author Ivan Krylosov
 */
public abstract class AbstractMvcFormOptionsService<FORM extends MvcForm> implements IFormOptionsService<FORM> {
    @Autowired
    @Qualifier(NavMenuOptionsPreparer.NAME)
    private IFormOptionsService<MvcForm> navbarOptionsPreparer;
    protected TranslatorService translatorService;
    @Autowired
    private RecaptchaKeyProperties recaptchaKeyProperties;

    @Override
    public void prepareFormOptions(FORM form) {
        navbarOptionsPreparer.prepareFormOptions(form);
        form.getModel().addAttribute("recaptcha_pb_key", recaptchaKeyProperties.publicKey());
    }

    protected abstract void setTranslatorService(TranslatorService translatorService);
}
