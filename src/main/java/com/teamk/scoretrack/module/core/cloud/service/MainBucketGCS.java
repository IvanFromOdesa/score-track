package com.teamk.scoretrack.module.core.cloud.service;

import com.google.cloud.storage.Bucket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MainBucketGCS extends GoogleCloudStorage {
    @Value("${google.cloud.bucket-main}")
    private String bucketName;
    @Override
    protected Bucket getBucket() {
        return storage.get(bucketName);
    }
}
