package com.teamk.scoretrack.module.security.geo.domain;

import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.history.domain.AuthenticationHistory;
import jakarta.persistence.Entity;

/*@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = { "country", "city" })
)*/
@Entity
public class LocationHistory extends AuthenticationHistory {
    private String country;
    private String city;
    private double lat;
    private double lng;

    public LocationHistory(AuthenticationBean authenticationBean, Status status) {
        super(authenticationBean, status);
    }

    protected LocationHistory() {

    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
