package com.geeks.pixion.services;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public interface S3Service {
    String uploadMediaToS3(String key, InputStream inputStream, long contentLength, String contentType, String fileRoot);
    String getS3Url(String key,String fileRoot);
    void deleteImage(String key,String fileRoot);
}
