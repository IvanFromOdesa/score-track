package com.teamk.scoretrack.module.security.geo.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "geo.enabled", havingValue = "false")
public class NoGeoLocationService implements IGeoLocationService{
    @Override
    public GeoResponse resolveLocation(String ip) {
        return GeoResponse.UNKNOWN;
    }
}
