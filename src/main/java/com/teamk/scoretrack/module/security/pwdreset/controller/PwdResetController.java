package com.teamk.scoretrack.module.security.pwdreset.controller;

import com.google.common.io.BaseEncoding;
import com.teamk.scoretrack.module.commons.base.controller.BaseMvcController;
import com.teamk.scoretrack.module.commons.base.service.valid.form.FormValidationContext;
import com.teamk.scoretrack.module.commons.form.mvc.MvcForm;
import com.teamk.scoretrack.module.commons.layout.alert.UiAlertDisplayOptionsUtils;
import com.teamk.scoretrack.module.commons.layout.alert.UiAlertType;
import com.teamk.scoretrack.module.commons.other.ErrorMap;
import com.teamk.scoretrack.module.security.auth.controller.AuthenticationController;
import com.teamk.scoretrack.module.security.auth.dto.PasswordForm;
import com.teamk.scoretrack.module.security.pwdreset.domain.PwdResetToken;
import com.teamk.scoretrack.module.security.pwdreset.dto.PwdResetForm;
import com.teamk.scoretrack.module.security.pwdreset.service.PwdResetService;
import com.teamk.scoretrack.module.security.pwdreset.service.form.PwdResetFormOptionsService;
import com.teamk.scoretrack.module.security.pwdreset.service.valid.PwdResetValidator;
import com.teamk.scoretrack.module.security.util.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

@Controller
public class PwdResetController extends BaseMvcController {
    public static final String PWD_FORGOT = "/forgot";
    private static final String PWD_RESET = "/reset";
    public static final String CONFIRM_URL_TOKEN = PWD_RESET + "/confirm";
    public static final String REQ_PWD_RESET = "/req" + PWD_RESET;
    public static final String NEW_PWD = PWD_RESET + "/new";
    public static final String PWD_RESET_CONFIRMED_URL_TOKEN = "passwordResetConfirmedUrlToken";
    private static final String PWD_RESET_DIR = "pwdreset";
    private static final String PWD_RESET_INDEX = PWD_RESET_DIR + "/index";
    private static final String NEW_PWD_INDEX = PWD_RESET_DIR + "/newPwd";

    private final PwdResetFormOptionsService formOptionsService;
    private final PwdResetService pwdResetService;
    private final PwdResetValidator pwdResetValidator;

    @Autowired
    public PwdResetController(PwdResetFormOptionsService formOptionsService,
                              PwdResetService pwdResetService,
                              PwdResetValidator pwdResetValidator) {
        this.formOptionsService = formOptionsService;
        this.pwdResetService = pwdResetService;
        this.pwdResetValidator = pwdResetValidator;
    }

    @GetMapping(PWD_FORGOT)
    public String initPage(Model model) {
        formOptionsService.prepareFormOptions(new MvcForm(model));
        model.addAttribute("pwdResetForm", new PwdResetForm());
        return PWD_RESET_INDEX;
    }

    @PostMapping(REQ_PWD_RESET)
    public String requestPasswordReset(HttpServletRequest request, RedirectAttributes redirectAttributes, @ModelAttribute PwdResetForm form) {
        ErrorMap errorMap = pwdResetValidator.validateEmail(new FormValidationContext<>(form));
        if (errorMap.isEmpty()) {
            String message = pwdResetService.requestPasswordReset(getBaseUrl().concat(CONFIRM_URL_TOKEN), form.getEmail(), HttpUtil.getClientIP(request));
            redirectAttributes.addFlashAttribute(UiAlertType.INFO.getName(), message);
        } else {
            UiAlertDisplayOptionsUtils.displayErrorAlert(redirectAttributes, errorMap);
        }
        return "redirect:".concat(PWD_FORGOT);
    }

    @GetMapping(CONFIRM_URL_TOKEN + "/{token}")
    public String confirmUrlToken(HttpSession session, @PathVariable String token, RedirectAttributes redirectAttributes) {
        Consumer<PwdResetToken> sessionBind = t -> session.setAttribute(PWD_RESET_CONFIRMED_URL_TOKEN, t);
        String message = pwdResetService.confirmValidUrlToken(new String(BaseEncoding.base64Url().decode(token), StandardCharsets.UTF_8), sessionBind);
        redirectAttributes.addFlashAttribute(UiAlertType.INFO.getName(), message);
        return "redirect:".concat(NEW_PWD);
    }

    @GetMapping(NEW_PWD)
    public String newPwdPage(Model model) {
        formOptionsService.prepareNewPwdFormOptions(new MvcForm(model));
        model.addAttribute("newPwdForm", new PasswordForm());
        return NEW_PWD_INDEX;
    }

    @PostMapping(NEW_PWD)
    public String confirmNewPassword(HttpSession session, RedirectAttributes redirectAttributes, @ModelAttribute PasswordForm form) {
        ErrorMap errorMap = pwdResetValidator.validate(new FormValidationContext<>(form));
        if (errorMap.isEmpty()) {
            String message = pwdResetService.resetPassword((PwdResetToken) session.getAttribute(PWD_RESET_CONFIRMED_URL_TOKEN), form.getPassword());
            redirectAttributes.addFlashAttribute(UiAlertType.INFO.getName(), message);
            return "redirect:".concat(AuthenticationController.LOGIN);
        } else {
            UiAlertDisplayOptionsUtils.displayErrorAlert(redirectAttributes, errorMap);
        }
        return "redirect:".concat(NEW_PWD);
    }
}
