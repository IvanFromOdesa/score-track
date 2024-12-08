package com.teamk.scoretrack.module.security.session.controller;

import com.teamk.scoretrack.module.commons.base.controller.SseController;
import com.teamk.scoretrack.module.security.session.event.SessionExpirationSubscriber;
import com.teamk.scoretrack.module.security.session.service.i18n.SessionTranslatorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.ExecutorService;

import static com.teamk.scoretrack.module.security.session.controller.SessionExpirationAlertSseController.SESSION_EXPIRATION_ALERT;
import static com.teamk.scoretrack.module.security.session.event.SessionAlertExpirationCallback.CHANNEL_NAMESPACE;

@RestController
@RequestMapping(SESSION_EXPIRATION_ALERT)
public class SessionExpirationAlertSseController extends SseController {
    public static final String SESSION_EXPIRATION_ALERT = BASE_URL + "/session/alert";
    public static final String SUBSCRIBE = "/subscribe";

    private final SessionTranslatorService translatorService;
    private final JedisPool jedisPool;
    private final ExecutorService executorService;

    @Autowired
    public SessionExpirationAlertSseController(SessionTranslatorService translatorService,
                                               JedisPool jedisPool,
                                               @Qualifier("baseThreadPoolExecutor") ExecutorService executorService) {
        this.translatorService = translatorService;
        this.jedisPool = jedisPool;
        this.executorService = executorService;
    }

    @GetMapping(value = SUBSCRIBE, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(HttpSession session) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        String sessionId = session.getId();

        executorService.execute(() -> {
            try (Jedis jedis = jedisPool.getResource()) {
                SessionExpirationSubscriber subscriber = new SessionExpirationSubscriber(
                        emitter,
                        sessionId,
                        translatorService
                );

                emitter.onCompletion(subscriber::unsubscribe);
                emitter.onTimeout(subscriber::unsubscribe);

                jedis.subscribe(subscriber, CHANNEL_NAMESPACE);
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });

        return emitter;
    }
}
