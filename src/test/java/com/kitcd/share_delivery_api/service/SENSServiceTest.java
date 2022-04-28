package com.kitcd.share_delivery_api.service;


import com.kitcd.share_delivery_api.dto.sens.SMSResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SENSServiceTest {

    @Autowired
    private SENSService sensService;

    @Test
    void sendSMS(){
        //given
        String to = "01085762079";
        String content = "test";

        //when
        SMSResponseDTO responseDTO = sensService.sendSMS(to, content);

        //then
        assertEquals(HttpStatus.ACCEPTED.value(), Integer.parseInt(responseDTO.getStatusCode()));

    }
}
