package com.teamk.scoretrack.module.core.api.commons.init.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.teamk.scoretrack.module.core.entities.SportAPI;
import com.teamk.scoretrack.module.core.entities.SportType;
import com.teamk.scoretrack.module.core.entities.io.AccessStatus;
import com.teamk.scoretrack.module.core.entities.user.client.domain.PlannedViewership;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public class ClientUserDataDto extends UserDataDto {
    private ProfileDto profile;
    private ViewershipPlanDto viewershipPlan;
    private Instant lastSeen;

    public ProfileDto getProfile() {
        return profile;
    }

    public void setProfile(ProfileDto profile) {
        this.profile = profile;
    }

    public ViewershipPlanDto getViewershipPlan() {
        return viewershipPlan;
    }

    public void setViewershipPlan(ViewershipPlanDto viewershipPlan) {
        this.viewershipPlan = viewershipPlan;
    }

    public Instant getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Instant lastSeen) {
        this.lastSeen = lastSeen;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ProfileDto {
        private String firstName;
        private String lastName;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @JsonSerialize(using = LocalDateSerializer.class)
        private LocalDate dob;
        private String nickname;
        private ProfileImgDto profileImg;
        private String bio;
        private List<SportType> sportPreference;
        private String instagramLink;
        private String xLink;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public LocalDate getDob() {
            return dob;
        }

        public void setDob(LocalDate dob) {
            this.dob = dob;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public ProfileImgDto getProfileImg() {
            return profileImg;
        }

        public void setProfileImg(ProfileImgDto profileImg) {
            this.profileImg = profileImg;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public List<SportType> getSportPreference() {
            return sportPreference;
        }

        public void setSportPreference(List<SportType> sportPreference) {
            this.sportPreference = sportPreference;
        }

        public String getInstagramLink() {
            return instagramLink;
        }

        public void setInstagramLink(String instagramLink) {
            this.instagramLink = instagramLink;
        }

        public String getxLink() {
            return xLink;
        }

        public void setxLink(String xLink) {
            this.xLink = xLink;
        }
    }

    public static class ProfileImgDto {
        private String url;
        private AccessStatus accessStatus;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public AccessStatus getAccessStatus() {
            return accessStatus;
        }

        public void setAccessStatus(AccessStatus accessStatus) {
            this.accessStatus = accessStatus;
        }
    }

    public static class ViewershipPlanDto {
        private Instant endDateTime;
        private boolean active;
        private List<SportAPI> customAvailableApis;
        private PlannedViewership plannedViewership;

        public Instant getEndDateTime() {
            return endDateTime;
        }

        public void setEndDateTime(Instant endDateTime) {
            this.endDateTime = endDateTime;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public List<SportAPI> getCustomAvailableApis() {
            return customAvailableApis;
        }

        public void setCustomAvailableApis(List<SportAPI> customAvailableApis) {
            this.customAvailableApis = customAvailableApis;
        }

        public PlannedViewership getPlannedViewership() {
            return plannedViewership;
        }

        public void setPlannedViewership(PlannedViewership plannedViewership) {
            this.plannedViewership = plannedViewership;
        }
    }
}
