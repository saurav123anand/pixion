package com.geeks.pixion.configs;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${s3.region}")
    private String region;
    @Value("${s3.accessKey}")
    private String accessKey;
    @Value("${s3.accessSecret}")
    private String accessSecret;
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public AmazonS3 amazonS3(){
        return AmazonS3ClientBuilder.standard().withRegion(region).
                withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey,accessSecret)))
                .withPathStyleAccessEnabled(true).build();
    }
}
