package com.kitcd.share_delivery_api.service;


import com.kitcd.share_delivery_api.dto.fcm.FCMDataType;
import com.kitcd.share_delivery_api.service.impl.FirebaseCloudMessageServiceImpl;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration(classes = {FirebaseCloudMessageServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class FirebaseCloudMessageServiceTest {

    @Autowired
    private FirebaseCloudMessageServiceImpl firebaseCloudMessageService;

    @Test
    void FCM_Push_메시지_테스트() throws IOException {

        //given
        String targetToken = "d5QmtyFjQ1eSQmC0tVgNv5:APA91bG79SkgaMV1XVtKAHVEJsqhvudSzy7_LLwZcrHOLgifydY_rviqLV0FQ9yXkMzh-CI8HpHNQa7P4Rfm0uzPkOLi-gWR6Q8AlFoivH4cO-Rrr_mod8p8RfJjeBSgcTwQ4WI4ReNV";
        String testMsgTitle = "test title";
        String testMsgBody = "test body";

        //when
        Response notificationMsgRes = firebaseCloudMessageService.sendMessageTo(targetToken, testMsgTitle, testMsgBody);
        Response dataMsgRes = firebaseCloudMessageService.sendMessageTo(targetToken, FCMDataType.CLOSE_RECRUIT);
        System.out.println("notificationMsgRes : " + notificationMsgRes);
        System.out.println("dataMsgRes : " + dataMsgRes);
        System.out.println("notification message :"+ firebaseCloudMessageService.makeNotificationMessage(targetToken, testMsgTitle, testMsgBody));
        System.out.println("Data message :"+ firebaseCloudMessageService.makeDataMessage(targetToken, FCMDataType.CLOSE_RECRUIT));

        //then
        assertTrue(notificationMsgRes.isSuccessful());
        assertTrue(dataMsgRes.isSuccessful());

    }
}
