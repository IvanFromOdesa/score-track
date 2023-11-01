package com.teamk.scoretrack.module.security.token.otp.service;

import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationEntityService;
import com.teamk.scoretrack.module.security.token.otp.ctx.OTPAuthContext;
import com.teamk.scoretrack.module.security.token.otp.domain.OTPToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OTPAuthService {
    private final AuthenticationEntityService authenticationEntityService;
    private final OTPTokenService otpTokenService;

    @Autowired
    public OTPAuthService(AuthenticationEntityService authenticationEntityService, OTPTokenService otpTokenService) {
        this.authenticationEntityService = authenticationEntityService;
        this.otpTokenService = otpTokenService;
    }

    public String generate(String authId, Long bhId) {
        return otpTokenService.cache(new OTPAuthContext(authId, bhId)).otp();
    }

    public boolean verifyOTP(AuthenticationBean authenticationBean, Long blockId, String otp) {
        Optional<OTPToken> hash = otpTokenService.get(authenticationBean.getId().toString());
        if (hash.isPresent() && hash.get().otp().equals(otp)) {
            authenticationEntityService.resolveBlockHistory(authenticationBean, blockId);
            return true;
        }
        return false;
    }
}
