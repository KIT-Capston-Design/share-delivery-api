package com.kitcd.share_delivery_api.service;


import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FirebaseCloudMessageServiceTest {

    @Autowired
    private FirebaseCloudMessageService firebaseCloudMessageService;

    @Test
    void FCM_Push_메시지_테스트() throws IOException {

        //given
        String targetToken = "DD3292C6-D2E1-48E7-9EA5-4C0961886D06";
        String testMsgTitle = "test title";
        String testMsgBody = "test body";

        //when
        Response response = firebaseCloudMessageService.sendMessageTo(targetToken, testMsgTitle, testMsgBody);
        System.out.println(response);
        //then
        assertTrue(response.isSuccessful());

    }
}
