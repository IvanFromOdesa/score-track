package com.teamk.scoretrack.module.core.entities.io.img;

import com.teamk.scoretrack.module.core.entities.io.ExternalFileData;
import com.teamk.scoretrack.module.core.entities.io.FileType;
import jakarta.persistence.Entity;

@Entity
public class ExternalImageData extends ExternalFileData {
    @Override
    public FileType getFileType() {
        return FileType.IMG;
    }
}
