package com.kitcd.share_delivery_api.service;


import com.kitcd.share_delivery_api.dto.fcm.FCMDataType;
import com.kitcd.share_delivery_api.service.impl.FirebaseCloudMessageServiceImpl;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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


        Map<String, Object> data = new HashMap<>();
        data.put("type", FCMDataType.CLOSE_RECRUIT);

        //when
        Response notificationMsgRes = firebaseCloudMessageService.sendMessageTo(targetToken, testMsgTitle, testMsgBody, null);
        Response dataMsgRes = firebaseCloudMessageService.sendMessageTo(targetToken, null, null, data);
        System.out.println("notificationMsgRes : " + notificationMsgRes);
        System.out.println("dataMsgRes : " + dataMsgRes);
        System.out.println("notification message :"+ firebaseCloudMessageService.makeMessage(targetToken, testMsgTitle, testMsgBody, null));
        System.out.println("Data message :"+ firebaseCloudMessageService.makeMessage(targetToken, null, null, data));

        //then
        assertTrue(notificationMsgRes.isSuccessful());
        assertTrue(dataMsgRes.isSuccessful());

    }
}
