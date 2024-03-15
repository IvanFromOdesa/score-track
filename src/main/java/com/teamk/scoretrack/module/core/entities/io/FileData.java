package com.teamk.scoretrack.module.core.entities.io;

import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationIdentifier;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class FileData extends AuthenticationIdentifier {
    private String externalUrl;
    private String internalUrl;
    private long size;
    private String extension;
    private String name;
    @CreationTimestamp
    private Instant uploadedAt;
    private AccessStatus accessStatus;
    private Instant reviewedAt;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by_id", referencedColumnName = "id")
    private AuthenticationBean reviewedBy;

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
}
