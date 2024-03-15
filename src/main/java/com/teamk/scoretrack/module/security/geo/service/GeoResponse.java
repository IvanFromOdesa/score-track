package com.teamk.scoretrack.module.security.geo.service;

public record GeoResponse(String country,
                          String city,
                          /* Country, city */
                          String location,
                          String isoCode,
                          Double latitude,
                          Double longitude) {
    public static final GeoResponse UNKNOWN = new GeoResponse("", "", "Unknown", "", (double) -1, (double) -1);
}
