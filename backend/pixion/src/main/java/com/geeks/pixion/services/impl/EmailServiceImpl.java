package com.geeks.pixion.services.impl;

import com.geeks.pixion.constants.PostType;
import com.geeks.pixion.services.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.net.URL;
import java.util.Objects;


@Service
public class EmailServiceImpl implements EmailService {

    Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendEmailWithAttachment(String from, String[] to, String[] cc, String subject, String senderName, String receiverName, MultipartFile file) {
        try{
            MimeMessage message=javaMailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setCc(cc);
            helper.setSubject(subject);
            // create a thymeleaf context to populate dynamic data
            Context context=new Context();
            context.setVariable("senderName",senderName);
            context.setVariable("receiverName",receiverName);
            // process the thymeleaf template with dynamic data
            String htmlContent=templateEngine.process("postUpload-template",context);
            helper.setText(htmlContent,true);
            helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file);
            javaMailSender.send(message);
        }
        catch (Exception e){
            e.printStackTrace();
            log.error(String.format("Error occurred while sending email: %s", e.getMessage()));
        }
    }

    @Override
    public void sendApprovedRejectPostEmail(String from, String[] to, String[] cc, String subject, String senderName, String receiverName,String mediaName,String mediaUrl,String action) {
        try{
            MimeMessage message=javaMailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setCc(cc);
            helper.setSubject(subject);
            // create a thymeleaf context to populate dynamic data
            Context context=new Context();
            context.setVariable("senderName",senderName);
            context.setVariable("receiverName",receiverName);
            // process the thymeleaf template with dynamic data
            String htmlContent="";
            if(action.equals(PostType.APPROVED.toString())){
                htmlContent=templateEngine.process("approvePost-template",context);
            }
            else if(action.equals(PostType.REJECTED.toString())){
                htmlContent=templateEngine.process("rejectPost-template",context);
            }
            helper.setText(htmlContent,true);
            UrlResource urlResource = new UrlResource(new URL(mediaUrl));
            helper.addAttachment(mediaName,urlResource);
            javaMailSender.send(message);
        }
        catch (Exception e){
            e.printStackTrace();
            log.error("Error occurred while sending email "+e.getMessage());
        }
    }
}
