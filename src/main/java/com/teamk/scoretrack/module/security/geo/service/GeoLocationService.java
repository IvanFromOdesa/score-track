package com.teamk.scoretrack.module.security.geo.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Location;
import com.teamk.scoretrack.module.commons.cache.CacheStore;
import com.teamk.scoretrack.module.commons.cache.caffeine.CacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;

@Service
@ConditionalOnProperty(value = "geo.enabled", havingValue = "true")
public class GeoLocationService implements IGeoLocationService {
    private final DatabaseReader databaseReader;

    @Autowired
    public GeoLocationService(DatabaseReader databaseReader) {
        this.databaseReader = databaseReader;
    }

    @Cacheable(cacheNames = {CacheStore.GEO_RESPONSE_CACHE_STORE}, key = "#ip", cacheManager = CacheConfig.CAFFEINE_CACHE_MANAGER)
    public GeoResponse resolveLocation(String ip) throws IOException, GeoIp2Exception {
        CityResponse cr = databaseReader.city(InetAddress.getByName(ip));
        Country country = cr.getCountry();
        /* Names */
        String countryName = country.getName();
        String cityName = cr.getCity().getName();
        /* lat, long */
        Location location = cr.getLocation();
        double latitude = location == null ? -1 : location.getLatitude();
        double longitude = location == null ? -1 : location.getLongitude();
        return new GeoResponse(countryName, cityName, formatLocation(cityName, countryName), country.getIsoCode(), latitude, longitude);
    }

    public static String formatLocation(String city, String country) {
        return city != null ? country.concat(", ").concat(city) : country;
    }
}
