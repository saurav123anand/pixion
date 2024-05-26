package com.geeks.pixion.services;

import org.springframework.web.multipart.MultipartFile;

public interface EmailService {
    void sendEmailWithAttachment(String from, String[] to, String[] cc, String subject,
                                 String senderName, String receiverName, MultipartFile file);
    void sendApprovedRejectPostEmail(String from, String[] to, String[] cc, String subject,
                               String senderName, String receiverName,String mediaName,String mediaUrl,String action);
}
