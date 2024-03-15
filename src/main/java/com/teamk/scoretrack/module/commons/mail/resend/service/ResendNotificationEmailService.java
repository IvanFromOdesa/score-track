package com.teamk.scoretrack.module.commons.mail.resend.service;

import com.teamk.scoretrack.module.commons.cache.redis.service.RedisEqualCtxService;
import com.teamk.scoretrack.module.commons.mail.IEmailService;
import com.teamk.scoretrack.module.commons.mail.NotificationEmail;
import com.teamk.scoretrack.module.commons.mail.resend.dao.ResendNotificationEmailDao;
import com.teamk.scoretrack.module.commons.mail.resend.domain.ResendNotificationEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.UnaryOperator;

@Service
public class ResendNotificationEmailService<RESEND_CTX extends ResendNotificationEmail> extends RedisEqualCtxService<RESEND_CTX, String, ResendNotificationEmailDao<RESEND_CTX>> {
    private final IEmailService<NotificationEmail> emailService;

    @Autowired
    public ResendNotificationEmailService(IEmailService<NotificationEmail> emailService) {
        this.emailService = emailService;
    }

    public void resend(String authId) {
        resend(authId, null);
    }

    public void resend(String authId, UnaryOperator<NotificationEmail> emailAlter) {
        get(authId).ifPresent(ctx -> {
            int attempt = ctx.getAttempt();
            if (attempt < ctx.getMaxAttempts()) {
                NotificationEmail email = ctx.getEmail();
                emailService.sendEmail(emailAlter != null ? emailAlter.apply(email) : email);
                ctx.setAttempt(attempt + 1);
                cache(ctx);
            } else {
                evict(authId);
            }
        });
    }

    public boolean sendOnce(RESEND_CTX resendCtx) {
        if (get(resendCtx.getId()).isEmpty()) {
            emailService.sendEmail(resendCtx.getEmail());
            return true;
        }
        return false;
    }

    @Override
    @Autowired
    protected void setDao(ResendNotificationEmailDao<RESEND_CTX> dao) {
        this.dao = dao;
    }
}
