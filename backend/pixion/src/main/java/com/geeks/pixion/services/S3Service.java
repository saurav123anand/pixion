package com.geeks.pixion.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public interface S3Service {
    String uploadMediaToS3(String key, InputStream inputStream, long contentLength, String contentType, String fileRoot);
    String getS3Url(String key,String fileRoot);
    void deleteImage(String key,String fileRoot);
}
