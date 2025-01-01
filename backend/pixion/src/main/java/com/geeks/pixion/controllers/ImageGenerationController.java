package com.geeks.pixion.controllers;

import com.geeks.pixion.payloads.ImageRequestDto;
import com.geeks.pixion.services.ImageGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class ImageGenerationController {
    @Autowired
    private ImageGenerationService imageGenerationService;
    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateImage(@RequestBody ImageRequestDto imageRequestDto) throws IOException {
        byte[] imageBytes = imageGenerationService.generateImage(imageRequestDto.getPrompt());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG); // Assuming the API returns PNG format
        headers.setContentLength(imageBytes.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(imageBytes);
    }

}
