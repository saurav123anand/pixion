package com.geeks.pixion.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {
    String uploadImage(MultipartFile multipartFile) throws IOException;
}
