package com.teamk.scoretrack.module.security.auth.service;

import com.teamk.scoretrack.module.commons.base.service.AbstractJpaEntityService;
import com.teamk.scoretrack.module.commons.cache.CacheStore;
import com.teamk.scoretrack.module.commons.cache.service.ManualCacheManager;
import com.teamk.scoretrack.module.commons.mail.EmailPrepareService;
import com.teamk.scoretrack.module.commons.mail.IEmailService;
import com.teamk.scoretrack.module.commons.mail.NotificationEmail;
import com.teamk.scoretrack.module.commons.util.log.MessageLogger;
import com.teamk.scoretrack.module.core.entities.user.base.event.UserProcessingEvent;
import com.teamk.scoretrack.module.core.entities.user.base.event.publisher.UserProcessingDelegator;
import com.teamk.scoretrack.module.core.entities.user.fan.ctx.FanProcessingContext;
import com.teamk.scoretrack.module.core.entities.user.fan.event.FanProcessingEvent;
import com.teamk.scoretrack.module.security.auth.dao.AuthenticationDao;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationStatus;
import com.teamk.scoretrack.module.security.auth.dto.AuthenticationDto;
import com.teamk.scoretrack.module.security.auth.service.i18n.AuthTranslatorService;
import com.teamk.scoretrack.module.security.commons.config.HashingConfiguration;
import com.teamk.scoretrack.module.security.history.domain.AuthenticationHistory;
import com.teamk.scoretrack.module.security.history.service.AuthenticationHistoryService;
import com.teamk.scoretrack.module.security.token.activation.domain.ActivationToken;
import com.teamk.scoretrack.module.security.token.activation.service.ActivationTokenService;
import com.teamk.scoretrack.module.security.token.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class AuthenticationEntityService extends AbstractJpaEntityService<AuthenticationBean, Long, AuthenticationDao> implements UserDetailsService {
    private final UserProcessingDelegator userProcessingDelegator;
    private final IEmailService<NotificationEmail> emailService;
    private final EmailPrepareService emailPrepareService;
    private final AuthTranslatorService translatorService;
    private final ActivationTokenService tokenService;
    private final ManualCacheManager manualCacheManager;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationHistoryService historyService;

    @Autowired
    public AuthenticationEntityService(UserProcessingDelegator userProcessingDelegator,
                                       IEmailService<NotificationEmail> emailService,
                                       EmailPrepareService emailPrepareService,
                                       AuthTranslatorService translatorService,
                                       ActivationTokenService tokenService,
                                       ManualCacheManager manualCacheManager,
                                       @Qualifier(HashingConfiguration.BCRYPT) PasswordEncoder passwordEncoder,
                                       AuthenticationHistoryService historyService) {
        this.userProcessingDelegator = userProcessingDelegator;
        this.emailService = emailService;
        this.emailPrepareService = emailPrepareService;
        this.translatorService = translatorService;
        this.tokenService = tokenService;
        this.manualCacheManager = manualCacheManager;
        this.passwordEncoder = passwordEncoder;
        this.historyService = historyService;
    }

    public String processSignUp(AuthenticationDto dto, String activationLink) {
        AuthenticationBean authenticationBean = getDefault(dto);
        userProcessingDelegator.processEvent(new FanProcessingEvent(FanProcessingContext.getDefault(authenticationBean), UserProcessingEvent.OperationType.CREATE));
        manualCacheManager.cache(CacheStore.AUTH_CACHE_STORE, authenticationBean.getId(), authenticationBean);
        sendActivationEmail(dto.email(), activationLink, generateActivationToken(authenticationBean));
        return translatorService.getMessage("success.message");
    }

    public String mockSignUp() {
        return translatorService.getMessage("success.message");
    }

    public AuthenticationBean getDefault(AuthenticationDto dto) {
        return AuthenticationBean.getDefault(dto.loginname(), passwordEncoder.encode(dto.password()), dto.email());
    }

    public void activate(UUID uuid) {
        long authId = Long.parseLong(tokenService.getActivationToken(uuid).authId());
        baseTransactionManager.doInNewTransaction(() -> {
            try {
                AuthenticationBean byId = manualCacheManager.evict(CacheStore.AUTH_CACHE_STORE, authId, AuthenticationBean.class, () -> getByIdOrThrow(authId));
                byId.setStatus(AuthenticationStatus.ACTIVATED);
                save(byId);
                tokenService.evict(uuid);
            } catch (Exception e) {
                MessageLogger.error(e.getMessage(), e);
            }
        });
    }

    public Long addAuthHistory(AuthenticationHistory history) {
        return baseTransactionManager.doInNewTransaction(status -> historyService.save(history));
    }

    public boolean resolveAuthHistory(AuthenticationBean authenticationBean, Long id) {
        return historyService.resolveAuthHistory(authenticationBean, id);
    }

    private UUID generateActivationToken(AuthenticationBean authenticationBean) {
        return tokenService.cache(authenticationBean).getId();
    }

    public void sendActivationEmail(AuthenticationBean authenticationBean, String link) {
        sendActivationEmail(authenticationBean.getEmail(), link, generateActivationToken(authenticationBean));
    }

    private void sendActivationEmail(String recipient, String link, UUID token) {
        String toActivate = link.concat("/%s".formatted(UUIDUtils.toBase64Url(token)));
        NotificationEmail email = emailPrepareService.prepareEmail(recipient, translatorService, toActivate, toActivate, TimeUnit.HOURS.convert(ActivationToken.TTL, TimeUnit.SECONDS));
        emailService.sendEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return dao.findByLoginname(username).orElseThrow(() -> new UsernameNotFoundException("%s not found.".formatted(username)));
    }

    @Override
    @Autowired
    protected void setDao(AuthenticationDao dao) {
        this.dao = dao;
    }
}
