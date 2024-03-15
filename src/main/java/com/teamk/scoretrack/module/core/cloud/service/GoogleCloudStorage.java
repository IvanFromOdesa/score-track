package com.teamk.scoretrack.module.core.cloud.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.teamk.scoretrack.module.commons.util.CommonsUtil;
import com.teamk.scoretrack.module.core.cloud.model.CloudId;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public abstract class GoogleCloudStorage implements CloudService<CloudId> {
    @Autowired
    protected Storage storage;

    @Override
    public CloudId save(byte[] src, String extension, String userDir) {
        String filename = generateFilename().concat(".").concat(extension);
        BlobId blobId = getBucket().create(userDir.concat(filename), src).getBlobId();
        return new CloudId(filename, blobId.toGsUtilUri());
    }

    public Blob save(byte[] src, String fullFilePath) {
        return getBucket().create(fullFilePath, src);
    }

    @NotNull
    private static String generateFilename() {
        return CommonsUtil.SRAlphanumeric(10, 25);
    }

    public Blob get(BlobId blobId) {
        return storage.get(blobId);
    }

    @Override
    public byte[] getBytes(CloudId id) {
        Blob blob = get(getBlobId(id));
        return blob != null && blob.exists() ? blob.getContent() : new byte[0];
    }

    @NotNull
    private BlobId getBlobId(CloudId id) {
        String url = id.getUrl();
        if (url != null) {
            return BlobId.fromGsUtilUri(url);
        }
        return BlobId.of(getBucket().getName(), id.getFilename());
    }

    @Override
    public InputStream getInputStream(CloudId id) {
        return new ByteArrayInputStream(getBytes(id));
    }

    @Override
    public boolean delete(CloudId id) {
        return storage.delete(BlobId.fromGsUtilUri(id.getUrl()));
    }

    protected abstract Bucket getBucket();
}
