package com.teamk.scoretrack.module.core.entities.user.client.dto;

import com.teamk.scoretrack.module.core.entities.sport_type.SportType;

import java.util.List;

public class ProfileUpdateDto {
    private String instagramLink;
    private String xLink;
    private String firstName;
    private String lastName;
    private String dob;
    private String bio;
    private List<SportType> sportPreference;
    private String nickname;

    public ProfileUpdateDto() {
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
