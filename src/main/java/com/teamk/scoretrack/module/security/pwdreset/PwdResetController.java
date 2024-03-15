package com.teamk.scoretrack.module.security.pwdreset;

import com.teamk.scoretrack.module.commons.base.controller.BaseMvcController;
import com.teamk.scoretrack.module.commons.form.mvc.MvcForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PwdResetController extends BaseMvcController {
    public static final String PWD_RESET = "/forgot";
    private static final String PWD_RESET_DIR = "pwdreset";
    private static final String PWD_RESET_INDEX = PWD_RESET_DIR + "/index";

    private final PwdResetFormOptionsService formOptionsService;

    @Autowired
    public PwdResetController(PwdResetFormOptionsService formOptionsService) {
        this.formOptionsService = formOptionsService;
    }

    @GetMapping(PWD_RESET)
    public String initPage(Model model) {
        formOptionsService.prepareFormOptions(new MvcForm(model));
        return PWD_RESET_INDEX;
    }
}
