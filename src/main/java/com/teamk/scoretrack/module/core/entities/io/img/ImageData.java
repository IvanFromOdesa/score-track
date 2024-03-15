package com.teamk.scoretrack.module.core.entities.io.img;

import com.teamk.scoretrack.module.core.entities.io.FileData;
import com.teamk.scoretrack.module.core.entities.io.FileType;
import jakarta.persistence.Entity;

@Entity
public class ImageData extends FileData {
    @Override
    public FileType getFileType() {
        return FileType.IMG;
    }
}
