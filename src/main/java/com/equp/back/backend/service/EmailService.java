package com.equp.back.backend.service;

import org.springframework.mail.javamail.JavaMailSender;

public interface EmailService extends JavaMailSender {
    void sendMessage(String text);
}
