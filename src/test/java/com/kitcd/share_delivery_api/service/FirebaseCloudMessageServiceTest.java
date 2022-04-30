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
        String targetToken = "d5QmtyFjQ1eSQmC0tVgNv5:APA91bG79SkgaMV1XVtKAHVEJsqhvudSzy7_LLwZcrHOLgifydY_rviqLV0FQ9yXkMzh-CI8HpHNQa7P4Rfm0uzPkOLi-gWR6Q8AlFoivH4cO-Rrr_mod8p8RfJjeBSgcTwQ4WI4ReNV";
        String testMsgTitle = "test title";
        String testMsgBody = "test body";

        //when
        Response response = firebaseCloudMessageService.sendMessageTo(targetToken, testMsgTitle, testMsgBody);
        System.out.println(response);
        //then
        assertTrue(response.isSuccessful());

    }
}
