package com.geeks.pixion.configs;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

@Configuration
public class AppConfig {

    @Value("${s3.region}")
    private String region;
    @Value("${s3.accessKey}")
    private String accessKey;
    @Value("${s3.accessSecret}")
    private String accessSecret;
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private String port;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String auth;
    @Value("${spring.mail.properties.mail.smtp.ssl.protocols}")
    private String protocol;
    @Value("${spring.mail.properties.mail.debug}")
    private String debug;

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
    @Bean
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl javaMailSender=new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(Integer.parseInt(port));
        Properties properties=javaMailSender.getJavaMailProperties();
        properties.put("mail.properties.mail.smtp.auth",auth);
        properties.put("mail.properties.mail.smtp.protocols",protocol);
        properties.put("mail.properties.mail.debug",debug);
        properties.put("mail.smtp.starttls.enable", "true");
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        return javaMailSender;
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
