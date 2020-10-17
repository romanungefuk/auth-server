package com.equp.back.backend.service.impl;

import com.equp.back.backend.service.EmailService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class EmailSendService implements EmailService {

    @Override
    public void sendMessage(String email) {
        System.out.println("это заглушка отправки письма");
    }
}
