package com.teamk.scoretrack.module.core.entities.user.client.domain;

import com.google.common.base.Objects;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

import java.io.Serializable;

@Entity
public class Socials {
    @EmbeddedId
    private SocialId socialId;
    /**
     * this is both a part of composite key and a foreign key to profile
     */
    @MapsId("profileId")
    @ManyToOne
    private Profile profile;
    private String profileUrl;

    public SocialId getSocialId() {
        return socialId;
    }

    public void setSocialId(SocialId socialId) {
        this.socialId = socialId;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String buildSocialLink() {
        return socialId.getSocialType().getPrefix().concat("/").concat(profileUrl);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Socials socials = (Socials) o;
        return Objects.equal(socialId, socials.socialId) && Objects.equal(profile, socials.profile) && Objects.equal(profileUrl, socials.profileUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(socialId, profile, profileUrl);
    }

    @Embeddable
    public static class SocialId implements Serializable {
        private Long profileId;
        private SocialType socialType;

        public Long getProfileId() {
            return profileId;
        }

        public void setProfileId(Long profileId) {
            this.profileId = profileId;
        }

        public SocialType getSocialType() {
            return socialType;
        }

        public void setSocialType(SocialType socialType) {
            this.socialType = socialType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SocialId socialId = (SocialId) o;
            return Objects.equal(profileId, socialId.profileId) && socialType == socialId.socialType;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(profileId, socialType);
        }
    }
}
