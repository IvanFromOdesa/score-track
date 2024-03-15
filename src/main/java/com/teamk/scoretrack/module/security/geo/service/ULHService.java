package com.teamk.scoretrack.module.security.geo.service;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.teamk.scoretrack.module.commons.cache.CacheStore;
import com.teamk.scoretrack.module.commons.mail.NotificationEmail;
import com.teamk.scoretrack.module.commons.util.log.MessageLogger;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.service.AuthenticationHolderService;
import com.teamk.scoretrack.module.security.geo.domain.LocationHistory;
import com.teamk.scoretrack.module.security.geo.event.UnknownLocationEvent;
import com.teamk.scoretrack.module.security.geo.event.publisher.UnknownLocationPublisher;
import com.teamk.scoretrack.module.security.history.domain.AuthenticationHistory;
import com.teamk.scoretrack.module.security.history.service.AuthenticationHistoryResolver;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@ConditionalOnProperty(value = "geo.enabled", havingValue = "true")
public class ULHService implements AuthenticationHistoryResolver {
    private final IGeoLocationService geoLocationService;
    private final AuthenticationHolderService authenticationHolderService;
    private final LocationHistoryService locationHistoryService;
    private final UnknownLocationPublisher unknownLocationPublisher;

    @Autowired
    public ULHService(IGeoLocationService geoLocationService, AuthenticationHolderService authenticationHolderService, LocationHistoryService locationHistoryService, UnknownLocationPublisher unknownLocationPublisher) {
        this.geoLocationService = geoLocationService;
        this.authenticationHolderService = authenticationHolderService;
        this.locationHistoryService = locationHistoryService;
        this.unknownLocationPublisher = unknownLocationPublisher;
    }

    @Cacheable(cacheNames = {CacheStore.AUTH_HISTORY_CACHE_STORE}, key = "#ip")
    @Override
    public AuthenticationHistory.Status resolve(HttpServletRequest request, String ip) {
        AuthenticationHistory.Status result = AuthenticationHistory.Status.RESOLVED;
        try {
            Optional<AuthenticationBean> currentAuthentication = authenticationHolderService.getCurrentAuthentication();
            if (currentAuthentication.isPresent()) {
                AuthenticationBean auth = currentAuthentication.get();
                GeoResponse geoResponse = geoLocationService.resolveLocation(ip);
                List<LocationHistory> byAuth = locationHistoryService.findTrusted(auth);
                if (!byAuth.isEmpty()) {
                    if (byAuth.stream().noneMatch(h -> GeoLocationService.formatLocation(h.getCity(), h.getCountry()).equals(geoResponse.location()))) {
                        prepareProcessEvent(auth, geoResponse, ip, request, Instant.now());
                        result = AuthenticationHistory.Status.BLOCKED;
                    }
                } else {
                    locationHistoryService.saveTrusted(auth, geoResponse);
                }
            }
        } catch (GeoIp2Exception | IOException e) {
            MessageLogger.error(e.getMessage());
            result = AuthenticationHistory.Status.UNDEFINED;
        }
        return result;
    }

    private void prepareProcessEvent(AuthenticationBean authenticationBean, GeoResponse geoResponse, String ip, HttpServletRequest request, Instant issuedAt) {
        UnknownLocationEvent event = new UnknownLocationEvent(new NotificationEmail().builder().recipient(authenticationBean.getEmail()).build(), issuedAt);
        event.setAuthentication(authenticationBean);
        event.setDefaults(geoResponse, ip, request);
        unknownLocationPublisher.processEvent(event);
    }
}
