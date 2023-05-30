package com.uniroma3.it.gastroguide.impl;

import com.uniroma3.it.gastroguide.constants.MailSubjects;
import com.uniroma3.it.gastroguide.constants.StaticURLs;
import com.uniroma3.it.gastroguide.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Service
@Qualifier("emailServiceImpl")
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendVerificationEmail(String to, String subject, String text) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            Resource resource = new ClassPathResource(MailSubjects.PATH_TO_MAIL_HTML_TEMPLATE.get(subject));
            String emailContent = new String(Files.readAllBytes(resource.getFile().toPath()), StandardCharsets.UTF_8);
            emailContent = emailContent.replace("{hostUrl}", StaticURLs.HOST_URL);
            emailContent = emailContent.replace("{token}", text);
            helper.setFrom("noreply@gastroguide.com");
            helper.setText(emailContent, true);
            emailSender.send(message);
        }catch (MessagingException | IOException e){
            System.out.println("ERRORE INVIO MAIL, errore a runtime, S K I P Z ! " + e);
        }
    }

    @Override
    public void sendPasswordResetEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            Resource resource = new ClassPathResource(MailSubjects.PATH_TO_RESET_MAIL_HTML_TEMPLATE.get(subject));
            String emailContent = new String(Files.readAllBytes(resource.getFile().toPath()), StandardCharsets.UTF_8);
            emailContent = emailContent.replace("{passwordToken}", text);

            System.out.println("------------");

            System.out.println(to);
            System.out.println(emailContent);
            System.out.println("------------");
            helper.setFrom("noreply@gastroguide.com");
            helper.setText(emailContent, true);
            emailSender.send(message);
        }catch (MessagingException | IOException e){
            System.out.println("ERRORE INVIO MAIL, errore a runtime, S K I P Z ! " + e);
        }
    }
}
