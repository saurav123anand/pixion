package com.geeks.pixion.services.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.geeks.pixion.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class S3ServiceImpl implements S3Service {
    @Autowired
    private AmazonS3 amazonS3;
    @Value("${s3.bucketName}")
    private String bucketName;
    @Value("${s3.region}")
    private String region;
    public String uploadMediaToS3(String key, InputStream inputStream, long contentLength, String contentType, String fileRoot){
        ObjectMetadata metadata=new ObjectMetadata();
        metadata.setContentLength(contentLength);
        metadata.setContentType(contentType);
        PutObjectRequest putObjectRequest=new PutObjectRequest(bucketName,fileRoot+"/"+key,inputStream,metadata)
                .withCannedAcl(CannedAccessControlList.PublicReadWrite);
        amazonS3.putObject(putObjectRequest);
        return getS3Url(key,fileRoot);
    }
    public String getS3Url(String key,String fileRoot){
        return String.format("https://%s.s3.%s.amazonaws.com/%s",bucketName,region,fileRoot+"/"+key);
    }
    public void deleteImage(String key,String fileRoot){
        amazonS3.deleteObject(new DeleteObjectRequest(bucketName,fileRoot+"/"+key));
    }
}
