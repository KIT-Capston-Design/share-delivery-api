package com.kitcd.share_delivery_api.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    void sendEmail() {
        //given
        String to = "khj745700@naver.com";

        //when
        emailService.sendEmail(to);
    }

    @Test
    void verificationEmail() {
    }

    @Test
    void accountAddEmail() {
    }
}