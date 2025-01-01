package com.geeks.pixion.services.impl;

import com.geeks.pixion.services.ImageGenerationService;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import com.geeks.pixion.constants.Constants;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageGenerationServiceImpl implements ImageGenerationService {
    @Value("${huggingface.api.Key}")
    private String apiKey;
    @Override
    public byte[] generateImage(String prompt) throws IOException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(Constants.HUGGING_FACE_API_URL);
            post.setHeader("Authorization", "Bearer " + apiKey);
            post.setHeader("Content-Type", "application/json");

            String jsonBody = "{\"inputs\": \"" + prompt + "\"}";
            post.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));

            try (CloseableHttpResponse response = client.execute(post)) {
                if (response.getCode() == 200) {
                    try (InputStream inputStream = response.getEntity().getContent()) {
                        return inputStream.readAllBytes();
                    }
                } else {
                    throw new RuntimeException("Failed to generate image. HTTP Code: " + response.getCode());
                }
            }
        }
    }
}
