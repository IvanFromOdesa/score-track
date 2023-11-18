package com.teamk.scoretrack.module.core.entities.user.base.domain;

import com.teamk.scoretrack.module.commons.base.domain.Identifier;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import org.hibernate.Length;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.Instant;
import java.util.List;

@Entity
public class Profile extends Identifier {
    private String firstName;
    private String lastName;
    private LocalDate dob;
    @CreationTimestamp
    private Instant createdOn;
    @UpdateTimestamp
    private Instant lastModified;
    private String nickname;
    private String profileUrl;
    @Column(length = Length.LONG)
    private String bio;
    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SportPreference> sportPreference;

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

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<SportPreference> getSportPreference() {
        return sportPreference;
    }

    public void setSportPreference(List<SportPreference> sportPreference) {
        this.sportPreference = sportPreference;
    }
}
