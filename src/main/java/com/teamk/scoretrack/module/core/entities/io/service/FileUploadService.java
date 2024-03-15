package com.teamk.scoretrack.module.core.entities.io.service;

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
import com.teamk.scoretrack.module.security.token.util.UUIDUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static com.teamk.scoretrack.module.core.entities.user.client.controller.ProfilePageController.PICTURE;
import static com.teamk.scoretrack.module.core.entities.user.client.controller.ProfilePageController.PROFILE;

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
        String fileExtension = Files.getFileExtension(file.getName());
        FileData fileData = getFileData(file, uploadedBy, fileExtension, uploadToCloud(file, fileExtension, uploadedBy));
        fileDataEntityService.save(fileData);
        return fileData;
    }

    public byte[] downloadFile(UUID externalUserId, String filename) {
        return cloudService.getBytes(CloudId.ofFilename(externalUserId.toString().concat("_").concat(filename)));
    }

    public byte[] downloadFile(Long fileId) {
        Optional<FileData> byId = fileDataEntityService.getById(fileId);
        return byId.isPresent() ? downloadFile(byId.get()) : new byte[0];
    }

    public byte[] downloadFile(FileData fileData) {
        return cloudService.getBytes(new CloudId(fileData.getName(), fileData.getInternalUrl()));
    }

    @NotNull
    private FileData getFileData(MultipartFile file, AuthenticationBean uploadedBy, String fileExtension, CloudId save) {
        String filename = save.getFilename();
        FileData fd = FD_EXTENSION_MAP.getOrDefault(FileType.byExtension(fileExtension), FileData::new).get();
        fd.setAuthenticationBean(uploadedBy);
        fd.setInternalUrl(save.getUrl());
        fd.setExternalUrl(generateExternalUrl(uploadedBy.getExternalId(), filename));
        fd.setAccessStatus(AccessStatus.REQUIRES_REVIEW);
        fd.setName(filename);
        fd.setExtension(fileExtension);
        fd.setSize(file.getSize());
        return fd;
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

    private static String generateExternalUrl(UUID externalUserId, String filename) {
        return PROFILE.concat(PICTURE).concat(UUIDUtils.toBase64Url(externalUserId)).concat("/").concat(filename);
    }
}
