package com.teamk.scoretrack.module.security.token.otp.controller;

import com.teamk.scoretrack.module.commons.controller.BaseMvcController;
import com.teamk.scoretrack.module.security.token.otp.service.OTPAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OtpAuthController extends BaseMvcController {
    public static final String RECOVER = "/recover";
    private static final String AUTH_DIR = "auth";
    private static final String RECOVER_PAGE = AUTH_DIR + RECOVER;
    private final OTPAuthService otpAuthService;

    @Autowired
    public OtpAuthController(OTPAuthService otpAuthService) {
        this.otpAuthService = otpAuthService;
    }

    @GetMapping(RECOVER)
    public String initPage() {
        return RECOVER_PAGE;
    }

    @PostMapping(RECOVER)
    public ResponseEntity<String> recover() {
        return null;
    }
}
