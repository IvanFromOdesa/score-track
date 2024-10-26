package com.teamk.scoretrack.module.security.pwdreset.service;

import com.google.common.io.BaseEncoding;
import com.teamk.scoretrack.module.commons.exception.ResourceNotFoundException;
import com.teamk.scoretrack.module.commons.mail.EmailPrepareService;
import com.teamk.scoretrack.module.commons.mail.IEmailService;
import com.teamk.scoretrack.module.commons.mail.NotificationEmail;
import com.teamk.scoretrack.module.core.entities.user.base.domain.Language;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationChangeCredentialsService;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationEntityService;
import com.teamk.scoretrack.module.security.pwdreset.domain.PwdResetToken;
import com.teamk.scoretrack.module.security.pwdreset.service.i18n.PwdResetTranslatorService;
import com.teamk.scoretrack.module.security.token.crypto.SecureRandomTokenInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Service
public class PwdResetService {
    private final PwdResetRedisService pwdResetRedisService;
    private final AuthenticationEntityService authenticationEntityService;
    private final AuthenticationChangeCredentialsService changeCredentialsService;
    private final PwdResetTranslatorService translatorService;
    private final IEmailService<NotificationEmail> emailService;
    private final EmailPrepareService emailPrepareService;
    @Value("${spring.application.name}")
    private String issuer;

    @Autowired
    public PwdResetService(PwdResetRedisService pwdResetRedisService,
                           AuthenticationEntityService authenticationEntityService,
                           AuthenticationChangeCredentialsService changeCredentialsService,
                           PwdResetTranslatorService translatorService,
                           IEmailService<NotificationEmail> emailService,
                           EmailPrepareService emailPrepareService) {
        this.pwdResetRedisService = pwdResetRedisService;
        this.authenticationEntityService = authenticationEntityService;
        this.changeCredentialsService = changeCredentialsService;
        this.translatorService = translatorService;
        this.emailService = emailService;
        this.emailPrepareService = emailPrepareService;
    }

    public String requestPasswordReset(String link, String email, String requestingIp) {
        authenticationEntityService.findByEmail(email).ifPresent(authenticationBean -> {
            String token = pwdResetRedisService.cache(new SecureRandomTokenInput(requestingIp, email, Instant.now())).getValue();
            sendResetPasswordEmail(link, authenticationBean, token);
        });
        return translatorService.getMessage("successTitle");
    }

    public String confirmValidUrlToken(String token, Consumer<PwdResetToken> sessionBind) {
        PwdResetToken pwdResetToken = getPwdResetToken(token);
        if (pwdResetToken.isUsed()) {
            throw new AccessDeniedException("Password reset token has already been used.");
        }
        changeCredentialsService.resetPasswordStatus(pwdResetToken.getEmail());
        pwdResetRedisService.markUsed(pwdResetToken);
        sessionBind.accept(pwdResetToken);
        return translatorService.getMessage("newPasswordTitle");
    }

    private PwdResetToken getPwdResetToken(String token) {
        return pwdResetRedisService.get(token).orElseThrow(ResourceNotFoundException::new);
    }

    public String resetPassword(PwdResetToken token, String newPassword) {
        if (!token.isUsed()) {
            throw new AccessDeniedException("The token must be confirmed first.");
        }
        changeCredentialsService.changePassword(newPassword, token.getEmail());
        pwdResetRedisService.evict(token.getValue());
        return translatorService.getMessage("successNewPwdTitle");
    }

    private void sendResetPasswordEmail(String link, AuthenticationBean authentication, String token) {
        String toActivate = link.concat("/%s".formatted(BaseEncoding.base64Url().encode(token.getBytes(StandardCharsets.UTF_8))));
        Language preferredLang = authentication.getUser().getPreferredLang();
        NotificationEmail email = emailPrepareService.prepareEmail(
                authentication.getEmail(),
                translatorService,
                preferredLang.getLocale(),
                authentication.getLoginname(),
                toActivate, toActivate,
                TimeUnit.MINUTES.convert(PwdResetToken.TTL, TimeUnit.SECONDS),
                issuer);
        emailService.sendEmail(email);
    }
}
