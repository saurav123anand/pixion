package com.geeks.pixion.services;

import java.io.IOException;

public interface ImageGenerationService {
    byte[] generateImage(String prompt) throws IOException;
}
