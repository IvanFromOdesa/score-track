package com.teamk.scoretrack.module.core.cloud.model;

public class CloudId {
    private String filename;
    private String url;

    public CloudId() {
    }

    public CloudId(String filename, String url) {
        this.filename = filename;
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static CloudId ofFilename(String filename) {
        CloudId cloudId = new CloudId();
        cloudId.setFilename(filename);
        return cloudId;
    }
}
