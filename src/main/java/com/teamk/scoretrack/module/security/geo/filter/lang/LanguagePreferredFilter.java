package com.teamk.scoretrack.module.security.geo.filter.lang;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.teamk.scoretrack.module.commons.util.log.MessageLogger;
import com.teamk.scoretrack.module.core.entities.user.base.domain.Language;
import com.teamk.scoretrack.module.security.filter.BaseSecurityFilter;
import com.teamk.scoretrack.module.security.geo.service.GeoLocationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

@Component
@ConditionalOnProperty(value = "geo.enabled", havingValue = "true")
public class LanguagePreferredFilter extends BaseSecurityFilter {
    private final GeoLocationService geoLocationService;

    @Autowired
    public LanguagePreferredFilter(GeoLocationService geoLocationService) {
        this.geoLocationService = geoLocationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Language language = Language.byIsoCode(geoLocationService.resolveLocation(getClientIP(request)).isoCode());
            if (language.isValid()) {
                WebUtils.setSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, language.getLocale());
            }
        } catch (GeoIp2Exception e) {
            MessageLogger.error(e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
