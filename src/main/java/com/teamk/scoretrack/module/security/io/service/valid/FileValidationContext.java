package com.teamk.scoretrack.module.security.io.service.valid;

import com.teamk.scoretrack.module.commons.base.service.valid.CommonValidationContext;
import com.teamk.scoretrack.module.core.entities.io.FileType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileValidationContext extends CommonValidationContext {
    private final MultipartFile multipartFile;
    private final List<FileType> allowedFileTypes;
    private String errorKey;

    public FileValidationContext(MultipartFile multipartFile, List<FileType> allowedFileTypes) {
        this.multipartFile = multipartFile;
        this.allowedFileTypes = allowedFileTypes;
        this.errorKey = "file";
    }

    public FileValidationContext(MultipartFile multipartFile, List<FileType> allowedFileTypes, String errorKey) {
        this.multipartFile = multipartFile;
        this.allowedFileTypes = allowedFileTypes;
        this.errorKey = errorKey;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public List<FileType> getAllowedFileTypes() {
        return allowedFileTypes;
    }

    public String getErrorKey() {
        return errorKey;
    }

    public void setErrorKey(String errorKey) {
        this.errorKey = errorKey;
    }
}
