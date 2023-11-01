package com.teamk.scoretrack.module.core.entities.user.base.domain;

import com.teamk.scoretrack.module.commons.domain.Identifier;
import com.teamk.scoretrack.module.core.entities.SportType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class SportPreference extends Identifier {
    @Enumerated
    private SportType sport;
    @ManyToOne
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
}
