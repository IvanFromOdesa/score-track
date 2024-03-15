package com.teamk.scoretrack.module.security.io.service.valid;

import com.teamk.scoretrack.module.commons.other.ErrorMap;
import com.teamk.scoretrack.module.commons.util.CommonsUtil;
import com.teamk.scoretrack.module.commons.util.log.MessageLogger;
import com.teamk.scoretrack.module.core.entities.io.FileType;
import com.teamk.scoretrack.module.security.io.service.FileSanitizer;
import com.teamk.scoretrack.module.security.io.service.i18n.IOTranslatorService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class DefaultFileValidator implements IFileValidator<FileValidationContext> {
    private final FileSanitizer fileSanitizer;
    private final IOTranslatorService IOTranslatorService;

    @Autowired
    public DefaultFileValidator(FileSanitizer fileSanitizer, IOTranslatorService IOTranslatorService) {
        this.fileSanitizer = fileSanitizer;
        this.IOTranslatorService = IOTranslatorService;
    }

    @Override
    public ErrorMap validate(FileValidationContext context) {
        ErrorMap errorMap = context.getErrorMap();
        MultipartFile multipartFile = context.getMultipartFile();
        List<FileType> allowedFileTypes = context.getAllowedFileTypes();
        String errorKey = context.getErrorKey();
        if (multipartFile.isEmpty()) {
            errorMap.put(errorKey, "file.empty", IOTranslatorService.getMessage("error.file.empty"));
        }
        if (isNonAllowedExtensions(allowedFileTypes, multipartFile.getContentType())) {
            errorMap.put(errorKey, "file.extension", IOTranslatorService.getMessage("error.fileExtension", getAllowedExtensions(allowedFileTypes)));
        }
        if (!isFileNameValid(allowedFileTypes, multipartFile.getOriginalFilename())) {
            errorMap.put(errorKey, "file.name", IOTranslatorService.getMessage("error.filename"));
        }
        try {
            if (!fileSanitizer.runThroughAntivirus(multipartFile)) {
                errorMap.put(errorKey, "file.malformed", IOTranslatorService.getMessage("error.filevirus"));
            }
        } catch (IOException e) {
            MessageLogger.error("Unexpected error while sanitizing file %s".formatted(multipartFile.getName()), e);
            errorMap.put(errorKey, "file.generic", IOTranslatorService.getMessage("error.generic"));
        }
        return errorMap;
    }

    @NotNull
    private String getAllowedExtensions(List<FileType> allowedFileTypes) {
        return Arrays.toString(allowedFileTypes.stream().map(f -> String.join(", ", f.getAllowedExtensions())).toArray(String[]::new)).replace("[", "").replace("]", "");
    }

    private static boolean isFileNameValid(List<FileType> allowedFileTypes, String filename) {
        return filename != null && allowedFileTypes.stream().anyMatch(e -> CommonsUtil.patternMatches(filename, e.getNameRegex()));
    }

    private static boolean isNonAllowedExtensions(List<FileType> allowedFileTypes, String contentType) {
        return allowedFileTypes.stream().anyMatch(e -> Arrays.stream(e.getAllowedExtensions()).noneMatch(ext -> ext.equals(contentType)));
    }
}
