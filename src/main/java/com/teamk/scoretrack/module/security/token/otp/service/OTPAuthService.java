package com.teamk.scoretrack.module.security.token.otp.service;

import com.teamk.scoretrack.module.commons.cache.CacheStore;
import com.teamk.scoretrack.module.commons.exception.ResourceNotFoundException;
import com.teamk.scoretrack.module.commons.mail.NotificationEmail;
import com.teamk.scoretrack.module.commons.mail.resend.domain.ResendNotificationEmail;
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
import java.util.function.Function;
import java.util.function.UnaryOperator;

@Service
public class OTPAuthService {
    private final AuthenticationEntityService authenticationEntityService;
    private final OTPTokenService otpTokenService;
    private final ResendNotificationEmailService<ResendNotificationEmail> resendNotificationEmailService;

    @Autowired
    public OTPAuthService(AuthenticationEntityService authenticationEntityService, OTPTokenService otpTokenService, ResendNotificationEmailService<ResendNotificationEmail> resendNotificationEmailService) {
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

    @CachePut(cacheNames = {CacheStore.AUTH_HISTORY_CACHE_STORE}, key = "#ip")
    public AuthenticationHistory.Status verifyOTP(AuthenticationBean authenticationBean, String otp, String ip) {
        return verifyOTP(authenticationBean.getExternalId().toString(), otp, otpToken -> {
            Long bhId = otpToken.getBhId();
            return bhId == null || authenticationEntityService.resolveAuthHistory(authenticationBean, bhId);
        });
    }

    public AuthenticationHistory.Status verifyOTP(String id, String otp, Function<OTPToken, Boolean> otpCheck) {
        Optional<OTPToken> byId = otpTokenService.get(id);
        if (byId.isPresent()) {
            OTPToken otpToken = byId.get();
            if (otpToken.getOtp().equals(otp) && otpCheck.apply(otpToken)) {
                otpTokenService.evict(id);
                resendNotificationEmailService.evict(id);
                return AuthenticationHistory.Status.RESOLVED;
            } else {
                return AuthenticationHistory.Status.BLOCKED;
            }
        }
        throw new ResourceNotFoundException();
    }
}
