package com.uniroma3.it.gastroguide.services;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendVerificationEmail(String to, String subject, String text) throws MessagingException;

    void sendPasswordResetEmail(String to, String subject, String text) throws  MessagingException;
}
