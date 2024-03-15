package com.teamk.scoretrack.module.commons.layout.navmenu;

import com.teamk.scoretrack.module.commons.form.IFormOptionsService;
import com.teamk.scoretrack.module.commons.form.mvc.MvcForm;
import com.teamk.scoretrack.module.commons.layout.LayoutTranslatorService;
import com.teamk.scoretrack.module.core.entities.user.base.domain.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service(NavMenuOptionsPreparer.NAME)
public class NavMenuOptionsPreparer implements IFormOptionsService<MvcForm> {
    public static final String NAME = "navbarOptionsPreparer";
    private final LayoutTranslatorService layoutTranslatorService;

    @Autowired
    public NavMenuOptionsPreparer(LayoutTranslatorService layoutTranslatorService) {
        this.layoutTranslatorService = layoutTranslatorService;
    }

    @Override
    public void prepareFormOptions(MvcForm form) {
        Model model = form.getModel();
        model.addAllAttributes(layoutTranslatorService.getMessages("nav_menu"));
        if (form.getAuthentication() == null || !form.getAuthentication().isAuthenticated()) {
            model.addAttribute("langs", Language.supported());
        }
    }
}
