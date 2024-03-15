package com.teamk.scoretrack.module.security.geo.service;

import com.maxmind.geoip2.exception.GeoIp2Exception;

import java.io.IOException;

public interface IGeoLocationService {
    GeoResponse resolveLocation(String ip) throws IOException, GeoIp2Exception;
}
