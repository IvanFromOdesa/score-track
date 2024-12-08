package com.teamk.scoretrack.module.security.session.event;

import com.teamk.scoretrack.module.core.entities.user.base.domain.Language;
import com.teamk.scoretrack.module.security.session.service.i18n.SessionTranslatorService;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import redis.clients.jedis.JedisPubSub;

import java.io.IOException;

import static com.teamk.scoretrack.module.security.session.event.SessionAlertExpirationCallback.CHANNEL_NAMESPACE;

public class SessionExpirationSubscriber extends JedisPubSub {
    private final SseEmitter emitter;
    private final String sessionId;
    private final SessionTranslatorService translatorService;

    public SessionExpirationSubscriber(SseEmitter emitter,
                                       String sessionId,
                                       SessionTranslatorService translatorService) {
        this.emitter = emitter;
        this.sessionId = sessionId;
        this.translatorService = translatorService;
    }

    @Override
    public void onMessage(String channel, String message) {
        if (channel.equals(CHANNEL_NAMESPACE)) {

            String[] parts = message.split(":");

            if (sessionId.equals(parts[2])) {
                synchronized (emitter) {
                    try {
                        emitter.send(SseEmitter
                                .event()
                                .name(CHANNEL_NAMESPACE)
                                .data(translatorService.getMessages(
                                        "expiration-alert",
                                        Language.byAlias(parts[1]).getLocale()
                                ))
                        );

                        this.emitter.complete();
                    } catch (IOException e) {
                        emitter.completeWithError(e);
                    }
                }
            }
        }
    }
}
