package com.teamk.scoretrack.module.security.token.otp.service;

import com.teamk.scoretrack.module.commons.cache.CacheStore;
import com.teamk.scoretrack.module.commons.exception.ResourceNotFoundException;
import com.teamk.scoretrack.module.commons.mail.NotificationEmail;
import com.teamk.scoretrack.module.commons.mail.resend.service.ResendNotificationEmailService;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationEntityService;
import com.teamk.scoretrack.module.security.history.domain.AuthenticationHistory;
import com.teamk.scoretrack.module.security.token.otp.ctx.OTPAuthContext;
import com.teamk.scoretrack.module.security.token.otp.domain.OTPToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.UnaryOperator;

@Service
public class OTPAuthService {
    private final AuthenticationEntityService authenticationEntityService;
    private final OTPTokenService otpTokenService;
    private final ResendNotificationEmailService resendNotificationEmailService;

    @Autowired
    public OTPAuthService(AuthenticationEntityService authenticationEntityService, OTPTokenService otpTokenService, ResendNotificationEmailService resendNotificationEmailService) {
        this.authenticationEntityService = authenticationEntityService;
        this.otpTokenService = otpTokenService;
        this.resendNotificationEmailService = resendNotificationEmailService;
    }

    public void resendOtp(String authId) {
        otpTokenService.evictAndGet(authId).ifPresent(otpToken -> {
            UnaryOperator<NotificationEmail> emailAlter = email -> {
                String replaced = email.getMessage().replace(otpToken.getOtp(), generate(authId, otpToken.getBhId()));
                email.setMessage(replaced);
                return email;
            };
            resendNotificationEmailService.resend(authId, emailAlter);
        });
    }

    public String generate(String authId, Long bhId) {
        return otpTokenService.cache(new OTPAuthContext(authId, bhId)).getOtp();
    }

    @CachePut(cacheNames = {CacheStore.LH_RESULT_CACHE_STORE}, key = "#ip")
    public AuthenticationHistory.Status verifyOTP(AuthenticationBean authenticationBean, String otp, String ip) {
        String authId = authenticationBean.getId().toString();
        Optional<OTPToken> byId = otpTokenService.get(authId);
        if (byId.isPresent()) {
            OTPToken otpToken = byId.get();
            if (otpToken.getOtp().equals(otp) && authenticationEntityService.resolveAuthHistory(authenticationBean, otpToken.getBhId())) {
                otpTokenService.evict(authId);
                resendNotificationEmailService.evict(authId);
                return AuthenticationHistory.Status.TRUSTED;
            } else {
                return AuthenticationHistory.Status.BLOCKED;
            }
        }
        throw new ResourceNotFoundException();
    }
}
