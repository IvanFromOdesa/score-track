package com.teamk.scoretrack.module.core.entities.io.event;

import com.teamk.scoretrack.module.core.entities.io.FileData;

public class FileUploadEvent {
    private FileData fileData;

    public FileData getFileData() {
        return fileData;
    }

    public void setFileData(FileData fileData) {
        this.fileData = fileData;
    }
}
