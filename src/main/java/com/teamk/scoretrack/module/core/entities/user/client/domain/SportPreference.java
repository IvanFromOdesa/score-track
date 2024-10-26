package com.teamk.scoretrack.module.core.entities.user.client.domain;

import com.google.common.base.Objects;
import com.teamk.scoretrack.module.commons.base.domain.Identifier;
import com.teamk.scoretrack.module.core.entities.sport_type.SportType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class SportPreference extends Identifier {
    @Enumerated
    private SportType sport;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public SportType getSport() {
        return sport;
    }

    public void setSport(SportType sport) {
        this.sport = sport;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SportPreference that = (SportPreference) o;
        return sport == that.sport && Objects.equal(profile, that.profile);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sport, profile);
    }
}
