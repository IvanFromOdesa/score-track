package com.teamk.scoretrack.module.security.geo.domain;

import com.google.common.hash.Hashing;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.history.domain.AuthenticationHistory;
import jakarta.persistence.Entity;

import java.nio.charset.StandardCharsets;

@Entity
public class LocationHistory extends AuthenticationHistory {
    private String ipHash;

    public LocationHistory(AuthenticationBean authenticationBean, Status status) {
        super(authenticationBean, status);
    }

    protected LocationHistory() {

    }

    public String getIpHash() {
        return ipHash;
    }

    public void setIpHash(String ipHash) {
        this.ipHash = ipHash;
    }

    public static String hashed(String ip) {
        return Hashing.sha256().hashString(ip, StandardCharsets.UTF_8).toString();
    }
}
