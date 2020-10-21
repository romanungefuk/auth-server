package com.equp.back.backend.service.impl;

import com.equp.back.backend.config.MyConstants;
import com.equp.back.backend.service.EmailService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;

@Service
@Slf4j
public class EmailSendService implements EmailService {


    @Autowired
    public JavaMailSender emailSender;

    SimpleMailMessage message = new SimpleMailMessage();
    @Override
    public void sendMessage(String email) {
        log.info("старт метода по отправке");

        message.setTo(email);
        message.setFrom(MyConstants.MY_EMAIL);
        message.setSubject("Смена пароля от сервиса EQup");
        message.setText("Добрый день! \n Ваш новый пароль: 333 \n Ждем Вас в нашем приложении! \n Команда EQup");
        this.emailSender.send(message);
    }

    @Override
    public MimeMessage createMimeMessage() {
        return null;
    }

    @Override
    public MimeMessage createMimeMessage(InputStream inputStream) throws MailException {
        return null;
    }

    @Override
    public void send(MimeMessage mimeMessage) throws MailException {

    }

    @Override
    public void send(MimeMessage... mimeMessages) throws MailException {

    }

    @Override
    public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {

    }

    @Override
    public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {

    }

    @Override
    public void send(SimpleMailMessage simpleMailMessage) throws MailException {

    }

    @Override
    public void send(SimpleMailMessage... simpleMailMessages) throws MailException {

    }
}
