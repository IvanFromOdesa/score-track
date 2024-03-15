package com.teamk.scoretrack.module.core.cloud.service;

import com.teamk.scoretrack.module.core.cloud.model.CloudId;

import java.io.InputStream;

/**
 * Generic interface for cloud services.
 * @param <ID> id type
 */
public interface CloudService<ID extends CloudId> {
    /**
     * Saves the file to cloud.
     * @param src src byte array
     * @return created cloud file
     */
    ID save(byte[] src, String extension, String userDir);

    /**
     * Gets file by id.
     * @param id id of the file
     * @return file byte array
     */
    byte[] getBytes(ID id);

    /**
     * Returns the file converted into {@link InputStream}
     * @param id id of the file
     * @return file converted to input stream
     */
    InputStream getInputStream(ID id);

    /**
     * Deletes the file by given id.
     * @param id id of the file
     */
    boolean delete(ID id);
}
