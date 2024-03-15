package com.teamk.scoretrack.module.core.entities.io;

import java.util.Arrays;

public enum FileType {
    UNKNOWN("", ""), IMG("[-_. A-Za-z0-9]{1,30}\\.(png|jpg|webp)", "image/jpeg", "image/png", "image/webp");

    private final String nameRegex;
    private final String[] allowedExtensions;

    FileType(String nameRegex, String... allowedExtensions) {
        this.nameRegex = nameRegex;
        this.allowedExtensions = allowedExtensions;
    }

    public String getNameRegex() {
        return nameRegex;
    }

    public String[] getAllowedExtensions() {
        return allowedExtensions;
    }

    public static FileType byExtension(String extension) {
        for (FileType ft: FileType.values()) {
            if (Arrays.stream(ft.getAllowedExtensions()).anyMatch(e -> e.contains(extension))) {
                return ft;
            }
        }
        return UNKNOWN;
    }
}
