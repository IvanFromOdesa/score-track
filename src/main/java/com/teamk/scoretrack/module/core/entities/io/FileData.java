package com.teamk.scoretrack.module.core.entities.io;

import com.teamk.scoretrack.module.commons.base.domain.IdAware;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

/**
 * Changed on 16.03.2024.
 * Id should be shared with {@link AuthenticationBean} via {@link com.teamk.scoretrack.module.security.auth.domain.AuthenticationIdentifier},
 * as one user can potentially upload many files via different parts of the system.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class FileData implements IdAware<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "uploaded_by", referencedColumnName = "id")
    private AuthenticationBean uploadedBy;
    @Column(unique = true)
    private String externalUrl;
    @Column(unique = true)
    private String internalUrl;
    private long size;
    private String extension;
    @Column(unique = true)
    private String name;
    @CreationTimestamp
    private Instant uploadedAt;
    private AccessStatus accessStatus;
    private Instant reviewedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by_id", referencedColumnName = "id")
    private AuthenticationBean reviewedBy;

    public void setId(Long id) {
        this.id = id;
    }

    public AuthenticationBean getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(AuthenticationBean uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    public String getInternalUrl() {
        return internalUrl;
    }

    public void setInternalUrl(String internalUrl) {
        this.internalUrl = internalUrl;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public AccessStatus getAccessStatus() {
        return accessStatus;
    }

    public void setAccessStatus(AccessStatus accessStatus) {
        this.accessStatus = accessStatus;
    }

    public Instant getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(Instant reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public AuthenticationBean getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(AuthenticationBean reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public FileType getFileType() {
        return FileType.UNKNOWN;
    }

    @Override
    public Long getId() {
        return id;
    }
}
