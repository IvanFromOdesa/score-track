package com.teamk.scoretrack.module.security.token.otp.controller;

import com.teamk.scoretrack.module.commons.base.controller.BaseMvcController;
import com.teamk.scoretrack.module.commons.form.mvc.MvcForm;
import com.teamk.scoretrack.module.security.auth.controller.AuthenticationController;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.history.domain.AuthenticationHistory;
import com.teamk.scoretrack.module.security.token.otp.dto.OtpForm;
import com.teamk.scoretrack.module.security.token.otp.service.OTPAuthService;
import com.teamk.scoretrack.module.security.token.otp.service.form.OtpFormOptionsService;
import com.teamk.scoretrack.module.security.util.HttpUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

@Controller
public class OtpAuthController extends BaseMvcController {
    public static final String RECOVER = "/recover";
    public static final String RESEND_OTP = "/otp/resend";
    private static final String OTP_DIR = "otp";
    private static final String RECOVER_PAGE = OTP_DIR + RECOVER;
    public static final String RECOVER_REDIRECT = "RECOVER_REDIRECT";
    public static final Set<String> PAGE_RESOURCES = Set.of(RESEND_OTP, "/js/otp.js", "/layouts/styles/otp/style.css", "/common.css");
    private final OTPAuthService otpAuthService;
    private final OtpFormOptionsService formOptionsService;

    @Autowired
    public OtpAuthController(OTPAuthService otpAuthService, OtpFormOptionsService formOptionsService) {
        this.otpAuthService = otpAuthService;
        this.formOptionsService = formOptionsService;
    }

    @GetMapping(RECOVER)
    public String initPage(Model model, Authentication authentication) {
        model.addAttribute("otpForm", new OtpForm());
        formOptionsService.prepareFormOptions(new MvcForm(model, authentication));
        return RECOVER_PAGE;
    }

    @PostMapping(RESEND_OTP)
    public ResponseEntity<?> resendOtp(Authentication authentication) {
        otpAuthService.resendOtp(((AuthenticationBean) authentication.getPrincipal()).getId().toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(RECOVER)
    public String recover(HttpServletRequest request, HttpServletResponse response, Authentication authentication, @ModelAttribute OtpForm otp) {
        AuthenticationHistory.Status result = otpAuthService.verifyOTP((AuthenticationBean) authentication.getPrincipal(), otp.getOtp(), HttpUtil.getClientIP(request));
        if (result.isTrusted()) {
            return "redirect:".concat(AuthenticationController.HOME);
        } else {
            response.addCookie(new Cookie(OtpAuthController.RECOVER_REDIRECT, ""));
            return "redirect:".concat(RECOVER + "?error");
        }
    }
}
