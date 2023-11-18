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
import com.teamk.scoretrack.module.security.service.DeviceMetadataResolver;
import com.teamk.scoretrack.module.security.util.HttpUtil;
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
public class ULHService {
    private final GeoLocationService geoLocationService;
    private final AuthenticationHolderService authenticationHolderService;
    private final LocationHistoryService locationHistoryService;
    private final UnknownLocationPublisher unknownLocationPublisher;

    @Autowired
    public ULHService(GeoLocationService geoLocationService, AuthenticationHolderService authenticationHolderService, LocationHistoryService locationHistoryService, UnknownLocationPublisher unknownLocationPublisher) {
        this.geoLocationService = geoLocationService;
        this.authenticationHolderService = authenticationHolderService;
        this.locationHistoryService = locationHistoryService;
        this.unknownLocationPublisher = unknownLocationPublisher;
    }

    @Cacheable(cacheNames = {CacheStore.LH_RESULT_CACHE_STORE}, key = "#ip")
    public AuthenticationHistory.Status processUserLocation(HttpServletRequest request, String ip) {
        AuthenticationHistory.Status result = AuthenticationHistory.Status.TRUSTED;
        try {
            GeoLocationService.GeoResponse geoResponse = geoLocationService.resolveLocation(ip);
            Optional<AuthenticationBean> currentAuthentication = authenticationHolderService.getCurrentAuthentication();
            if (currentAuthentication.isPresent()) {
                AuthenticationBean auth = currentAuthentication.get();
                String hashed = LocationHistory.hashed(ip);
                List<LocationHistory> byAuth = locationHistoryService.findTrusted(auth);
                if (!byAuth.isEmpty()) {
                    if (byAuth.stream().noneMatch(h -> h.getIpHash().equals(hashed))) {
                        prepareProcessEvent(auth, geoResponse, ip, request, Instant.now());
                        result = AuthenticationHistory.Status.BLOCKED;
                    }
                } else {
                    locationHistoryService.saveTrusted(auth, hashed);
                }
            }
        } catch (GeoIp2Exception | IOException e) {
            MessageLogger.error(e.getMessage());
            result = AuthenticationHistory.Status.UNDEFINED;
        }
        return result;
    }

    private void prepareProcessEvent(AuthenticationBean authenticationBean, GeoLocationService.GeoResponse geoResponse, String ip, HttpServletRequest request, Instant issuedAt) {
        UnknownLocationEvent event = new UnknownLocationEvent(new NotificationEmail().builder().recipient(authenticationBean.getEmail()).build(), issuedAt);
        event.setAuthenticationBean(authenticationBean);
        event.setAttemptedCountry(geoResponse.country());
        event.setAttemptedCity(geoResponse.city());
        event.setAttemptedDevice(DeviceMetadataResolver.getDeviceMD(request));
        event.setAttemptedIp(ip);
        // TODO
        event.setRecoveryLink(HttpUtil.getBaseUrl(request).concat(""));
        unknownLocationPublisher.processEvent(event);
    }
}
