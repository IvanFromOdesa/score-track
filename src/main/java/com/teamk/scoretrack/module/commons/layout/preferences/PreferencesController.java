package com.teamk.scoretrack.module.commons.layout.preferences;

import com.teamk.scoretrack.module.commons.base.controller.BaseMvcController;
import com.teamk.scoretrack.module.core.entities.user.base.domain.Language;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

@Controller
@RequestMapping(value = PreferencesController.PREF)
public class PreferencesController extends BaseMvcController {
    public static final String PREF = "/preferences";
    public static final String LANG = "/lang";

    @PostMapping(LANG + "/{key}")
    public ResponseEntity<?> setLanguagePreference(@PathVariable String key, HttpServletRequest request) {
        Language language = Language.byCode(key);
        if (language.isValid()) {
            WebUtils.setSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, language.getLocale());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
