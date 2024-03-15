package com.teamk.scoretrack.module.security.recaptcha.service;

import com.teamk.scoretrack.module.commons.mail.NotificationEmail;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationWrapper;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationHolderService;
import com.teamk.scoretrack.module.security.history.domain.AuthenticationHistory;
import com.teamk.scoretrack.module.security.history.service.AuthenticationHistoryResolver;
import com.teamk.scoretrack.module.security.recaptcha.event.RecaptchaFailureEvent;
import com.teamk.scoretrack.module.security.recaptcha.event.publisher.RecaptchaFailurePublisher;
import com.teamk.scoretrack.module.security.recaptcha.exception.RecaptchaInvalidResponse;
import com.teamk.scoretrack.module.security.recaptcha.model.RecaptchaResponse;
import com.teamk.scoretrack.module.security.recaptcha.service.external.RecaptchaExternalService;
import com.teamk.scoretrack.module.security.util.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class RecaptchaResponseResolveService implements AuthenticationHistoryResolver {
    private final RecaptchaExternalService recaptchaExternalService;
    private final AuthenticationHolderService authenticationHolderService;
    private final RecaptchaFailurePublisher recaptchaFailurePublisher;
    public static final String RECAPTCHA_CLIENT_RESPONSE = "g-recaptcha-response";
    public static final double RECAPTCHA_SCORE_THRESHOLD = 0.5;

    @Autowired
    public RecaptchaResponseResolveService(RecaptchaExternalService recaptchaExternalService,
                                           AuthenticationHolderService authenticationHolderService,
                                           RecaptchaFailurePublisher recaptchaFailurePublisher) {
        this.recaptchaExternalService = recaptchaExternalService;
        this.authenticationHolderService = authenticationHolderService;
        this.recaptchaFailurePublisher = recaptchaFailurePublisher;
    }

    @Override
    public AuthenticationHistory.Status resolve(HttpServletRequest request, String ip) {
        AuthenticationHistory.Status result = AuthenticationHistory.Status.RESOLVED;
        Optional<RecaptchaResponse> response = recaptchaExternalService.callGoogleRecaptcha(request.getParameter(RECAPTCHA_CLIENT_RESPONSE));
        if (response.isPresent()) {
            RecaptchaResponse recaptchaResponse = response.get();
            String action = recaptchaResponse.action();
            if (!recaptchaResponse.success() || action == null || !HttpUtil.getRequestedUri(request).endsWith(action)) {
                throw new RecaptchaInvalidResponse(String.join("", recaptchaResponse.errorCodes()));
            }
            double score = recaptchaResponse.score();
            if (score < RECAPTCHA_SCORE_THRESHOLD) {
                Optional<AuthenticationWrapper> wrapper = authenticationHolderService.getAuthenticationWrapper();
                if (wrapper.isPresent()) {
                    prepareProcessEvent(wrapper.get(), score, recaptchaResponse, request, ip, Instant.now());
                    result = AuthenticationHistory.Status.BLOCKED;
                } else {
                    throw new RecaptchaInvalidResponse("Recaptcha failure unauthorized.");
                }
            }
        } else {
            result = AuthenticationHistory.Status.UNDEFINED;
        }
        return result;
    }

    private void prepareProcessEvent(AuthenticationWrapper wrapper, double score, RecaptchaResponse recaptchaResponse, HttpServletRequest request, String ip, Instant issuedAt) {
        RecaptchaFailureEvent event = new RecaptchaFailureEvent(new NotificationEmail().builder().recipient(wrapper.email()).build(), issuedAt);
        event.setDefaults(null, ip, request);
        event.setAuthentication(wrapper);
        event.setScore(score);
        event.setTimestamp(recaptchaResponse.challenge_ts());
        event.setAction(recaptchaResponse.action());
        recaptchaFailurePublisher.processEvent(event);
    }
}
