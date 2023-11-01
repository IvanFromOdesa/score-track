package com.teamk.scoretrack.module.security.geo.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Country;
import com.teamk.scoretrack.module.commons.cache.CacheStore;
import com.teamk.scoretrack.module.commons.cache.caffeine.CacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;

@Service
public class GeoLocationService {
    private final DatabaseReader databaseReader;

    @Autowired
    public GeoLocationService(DatabaseReader databaseReader) {
        this.databaseReader = databaseReader;
    }

    @Cacheable(cacheNames = {CacheStore.GEO_RESPONSE}, key = "#ip", cacheManager = CacheConfig.CAFFEINE_CACHE_MANAGER)
    public GeoResponse resolveLocation(String ip) throws IOException, GeoIp2Exception {
        CityResponse cr = databaseReader.city(InetAddress.getByName(ip));
        Country country = cr.getCountry();
        return new GeoResponse(country.getName(), cr.getCity().getName(), country.getIsoCode());
    }

    public record GeoResponse(String country, String city, String isoCode) implements Serializable {

    }
}
