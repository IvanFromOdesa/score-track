package com.teamk.scoretrack.module.security.service;

import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;

public final class DeviceMetadataResolver {
    public static String getDeviceMD(HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        return userAgent.getOperatingSystem().getDeviceType().getName() + ", " + userAgent.getBrowser().getName();
    }
}
