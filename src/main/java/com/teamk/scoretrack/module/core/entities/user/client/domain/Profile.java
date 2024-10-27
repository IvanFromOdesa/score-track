package com.teamk.scoretrack.module.core.entities.user.client.domain;

import com.teamk.scoretrack.module.commons.base.domain.Identifier;
import com.teamk.scoretrack.module.core.entities.io.img.ExternalImageData;
import com.teamk.scoretrack.module.core.entities.io.img.ImageData;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import org.hibernate.Length;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_img_id", referencedColumnName = "id")
    private ImageData profileImg;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "external_profile_img_id", referencedColumnName = "id")
    private ExternalImageData externalProfileImg;
    @Column(length = Length.LONG)
    private String bio;
    /**
     * <a href="https://discourse.hibernate.org/t/a-collection-with-cascade-all-delete-orphan-was-no-longer-referenced-by-the-owning-entity-instance/6638/5">Issue</a>
     * In short, do not call implicit setter for collection with set cascade = all and orphanRemoval = true.
     * Do not directly mutate the collection object (e.g. setting a new one) as it is observed by Hibernate.
     * To add or remove child objects, call methods on collection itself.
     */
    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SportPreference> sportPreference;
    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Socials> socials;

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

    public ImageData getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(ImageData profileImg) {
        this.profileImg = profileImg;
    }

    public ExternalImageData getExternalProfileImg() {
        return externalProfileImg;
    }

    public void setExternalProfileImg(ExternalImageData externalProfileImg) {
        this.externalProfileImg = externalProfileImg;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Set<SportPreference> getSportPreference() {
        return sportPreference;
    }

    public void setSportPreference(Set<SportPreference> sportPreference) {
        this.sportPreference = sportPreference;
    }

    public Set<Socials> getSocials() {
        return socials;
    }

    public void setSocials(Set<Socials> socials) {
        this.socials = socials;
    }
}
