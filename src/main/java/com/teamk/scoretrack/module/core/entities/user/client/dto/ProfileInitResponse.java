package com.teamk.scoretrack.module.core.entities.user.client.dto;

import com.teamk.scoretrack.module.core.api.commons.base.BundleResponse;

public class ProfileInitResponse extends BundleResponse {
    private String maxUploadFileSize;

    public String getMaxUploadFileSize() {
        return maxUploadFileSize;
    }

    public void setMaxUploadFileSize(String maxUploadFileSize) {
        this.maxUploadFileSize = maxUploadFileSize;
    }
}
