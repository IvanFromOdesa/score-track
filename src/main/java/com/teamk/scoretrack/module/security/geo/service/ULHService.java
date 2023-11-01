package com.teamk.scoretrack.module.security.geo.service;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.teamk.scoretrack.module.commons.service.mail.NotificationEmail;
import com.teamk.scoretrack.module.commons.util.log.MessageLogger;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.geo.domain.LocationHistory;
import com.teamk.scoretrack.module.security.geo.event.UnknownLocationEvent;
import com.teamk.scoretrack.module.security.geo.event.publisher.UnknownLocationPublisher;
import com.teamk.scoretrack.module.security.service.DeviceMetadataResolver;
import com.teamk.scoretrack.module.security.util.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Service
@ConditionalOnProperty(value = "geo.enabled", havingValue = "true")
public class ULHService {
    private final GeoLocationService geoLocationService;
    private final LocationHistoryService locationHistoryService;
    private final UnknownLocationPublisher unknownLocationPublisher;

    @Autowired
    public ULHService(GeoLocationService geoLocationService, LocationHistoryService locationHistoryService, UnknownLocationPublisher unknownLocationPublisher) {
        this.geoLocationService = geoLocationService;
        this.locationHistoryService = locationHistoryService;
        this.unknownLocationPublisher = unknownLocationPublisher;
    }

    public void processUserLocation(HttpServletRequest request, HttpServletResponse response, String ip) {
        try {
            GeoLocationService.GeoResponse geoResponse = geoLocationService.resolveLocation(ip);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                AuthenticationBean auth = (AuthenticationBean) authentication.getPrincipal();
                String hashed = LocationHistory.hashed(ip);
                List<LocationHistory> byIp = locationHistoryService.findTrusted(auth);
                if (!byIp.isEmpty()) {
                    if (byIp.stream().noneMatch(h -> h.getIpHash().equals(hashed))) {
                        prepareProcessEvent(auth, geoResponse, ip, DeviceMetadataResolver.getDeviceMD(request), Instant.now());
                        response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
                        response.setHeader(HttpHeaders.LOCATION, response.encodeRedirectURL(HttpUtil.getBaseUrl(request).concat("")));
                    }
                } else {
                    locationHistoryService.saveTrusted(auth, hashed);
                }
            }
        } catch (GeoIp2Exception | IOException e) {
            MessageLogger.error(e.getMessage());
        }
    }

    private void prepareProcessEvent(AuthenticationBean authenticationBean, GeoLocationService.GeoResponse geoResponse, String ip, String device, Instant issuedAt) {
        UnknownLocationEvent event = new UnknownLocationEvent(new NotificationEmail().builder().recipient(authenticationBean.getEmail()).build(), issuedAt);
        event.setAuthenticationBean(authenticationBean);
        event.setAttemptedCountry(geoResponse.country());
        event.setAttemptedCity(geoResponse.city());
        event.setAttemptedDevice(device);
        event.setAttemptedIp(ip);
        unknownLocationPublisher.processEvent(event);
    }
}
