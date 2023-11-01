package com.teamk.scoretrack.module.security.auth.service.form;

import com.teamk.scoretrack.module.commons.service.form.mvc.AbstractMvcFormOptionsService;
import com.teamk.scoretrack.module.commons.service.form.mvc.MvcForm;
import com.teamk.scoretrack.module.security.auth.service.i18n.AuthTranslatorService;
import com.teamk.scoretrack.module.security.service.jwt.JWTResolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class AuthFormOptionsService extends AbstractMvcFormOptionsService {
    private final AuthTranslatorService translatorService;
    private final JWTResolverService jwtResolver;

    @Autowired
    public AuthFormOptionsService(AuthTranslatorService translatorService, JWTResolverService jwtResolver) {
        this.translatorService = translatorService;
        this.jwtResolver = jwtResolver;
    }

    @Override
    public void prepareFormOptions(MvcForm mvcForm) {
        Model model = mvcForm.model();
        Authentication authentication = mvcForm.authentication();
        model.addAllAttributes(translatorService.getMessages(mvcForm.bundleName()));
        if (authentication != null) {
            model.addAttribute("token", jwtResolver.generateToken(authentication));
        }
    }
}
