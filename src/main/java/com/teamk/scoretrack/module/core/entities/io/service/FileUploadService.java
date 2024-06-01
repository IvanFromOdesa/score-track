package com.teamk.scoretrack.module.core.entities.io.service;

import com.google.common.io.BaseEncoding;
import com.google.common.io.Files;
import com.teamk.scoretrack.module.commons.exception.ServerException;
import com.teamk.scoretrack.module.commons.util.log.MessageLogger;
import com.teamk.scoretrack.module.core.cloud.model.CloudId;
import com.teamk.scoretrack.module.core.cloud.service.CloudService;
import com.teamk.scoretrack.module.core.entities.io.AccessStatus;
import com.teamk.scoretrack.module.core.entities.io.FileData;
import com.teamk.scoretrack.module.core.entities.io.FileType;
import com.teamk.scoretrack.module.core.entities.io.img.ImageData;
import com.teamk.scoretrack.module.security.auth.domain.AuthenticationBean;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class FileUploadService {
    private final CloudService<CloudId> cloudService;
    private final FileDataEntityService fileDataEntityService;
    private static final Map<FileType, Supplier<FileData>> FD_EXTENSION_MAP = Map.of(
            FileType.IMG, ImageData::new
    );

    @Autowired
    public FileUploadService(CloudService<CloudId> cloudService,
                             FileDataEntityService fileDataEntityService) {
        this.cloudService = cloudService;
        this.fileDataEntityService = fileDataEntityService;
    }

    public FileData uploadFile(MultipartFile file, AuthenticationBean uploadedBy) {
        return uploadFile(file, uploadedBy, FileUtils::getDefaultExternalDownloadUrl);
    }

    /**
     * Uploads the file as blob to the cloud while persisting its metadata into db.
     * @param file file to upload
     * @param uploadedBy associated principal
     * @param externalUrl file server url
     * @return file metadata object
     */
    public FileData uploadFile(MultipartFile file, AuthenticationBean uploadedBy, Supplier<String> externalUrl) {
        String fileExtension = Files.getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
        FileData fileData = getFileData(file, uploadedBy, file.getContentType(), uploadToCloud(file, fileExtension, uploadedBy), externalUrl);
        fileDataEntityService.save(fileData);
        return fileData;
    }

    public byte[] downloadFile(String filename) {
        return downloadFile(() -> fileDataEntityService.getByName(filename));
    }

    public byte[] downloadFile(Long fileId) {
        return downloadFile(() -> fileDataEntityService.getById(fileId));
    }

    private byte[] downloadFile(Supplier<Optional<FileData>> getByCallback) {
        Optional<FileData> byCallback = getByCallback.get();
        if (byCallback.isPresent()) {
            FileData fileData = byCallback.get();
            if (fileData.getAccessStatus().isAccessible()) {
                return downloadFile(fileData);
            }
        }
        return new byte[0];
    }

    private byte[] downloadFile(FileData fileData) {
        return cloudService.getBytes(new CloudId(fileData.getName(), fileData.getInternalUrl()));
    }

    @NotNull
    private FileData getFileData(MultipartFile file, AuthenticationBean uploadedBy, String fileExtension, CloudId save, Supplier<String> externalUrl) {
        String filename = save.getFilename();
        FileData fd = FD_EXTENSION_MAP.getOrDefault(FileType.byExtension(fileExtension), FileData::new).get();
        fd.setUploadedBy(uploadedBy);
        fd.setInternalUrl(save.getUrl());
        fd.setExternalUrl(generateExternalUrl(externalUrl, filename));
        fd.setAccessStatus(AccessStatus.REQUIRES_REVIEW);
        fd.setName(filename);
        fd.setExtension(fileExtension);
        fd.setSize(file.getSize());
        return fd;
    }

    @NotNull
    private static String generateExternalUrl(Supplier<String> externalUrl, String filename) {
        return externalUrl.get().concat(BaseEncoding.base64Url().encode(filename.getBytes()));
    }

    private CloudId uploadToCloud(MultipartFile file, String extension, AuthenticationBean uploadedBy) {
        CloudId save;
        try {
            save = cloudService.save(file.getBytes(), extension, uploadedBy.getExternalId().toString().concat("_"));
        } catch (IOException e) {
            MessageLogger.error(e.getMessage());
            throw new ServerException(e);
        }
        return save;
    }
}
