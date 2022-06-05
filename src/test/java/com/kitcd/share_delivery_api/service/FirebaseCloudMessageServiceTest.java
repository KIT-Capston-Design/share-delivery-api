package com.kitcd.share_delivery_api.service;


import com.kitcd.share_delivery_api.domain.jpa.common.State;
import com.kitcd.share_delivery_api.dto.fcm.FCMDataType;
import com.kitcd.share_delivery_api.dto.fcm.FCMGroupRequest;
import com.kitcd.share_delivery_api.service.impl.FirebaseCloudMessageServiceImpl;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration(classes = {FirebaseCloudMessageServiceImpl.class})
@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "hyunkim")
@SpringBootTest(classes = {FirebaseCloudMessageServiceImpl.class})
public class FirebaseCloudMessageServiceTest {

    @Autowired
    private FirebaseCloudMessageServiceImpl firebaseCloudMessageService;

    @Test
    void FCM_Push_메시지_테스트() {

        //given
        String groupToken = "APA91bGYfw74zqK5iGz8gxEPg0JG2QB0iSlm96MLXTlPQP9-L84PVz-2Z1ia00TQ4TV3ysGS1ksGJlbI4ctYwcJb7Hvid7sc06H7gWEvIGJg70RIPPGKxmKwvf1dCrB5DpfwVvy6tqjD";
        String targetToken = "dbjwOtyLQsq7n1G4HFshEn:APA91bEF7TPtkq0uU-kqGtamEv-Ru76r1D98Vu3BA4Qh10ebj1ZoCvAOxQWbbe3dG4iiHeDhPnfGWhVQvMoJc8Pilvo9ScqJUHNpfOOHt1LB4-rbRM-D9-Up4hGD618R8jsizOjFZbV4";
        String testMsgTitle = "test title";
        String testMsgBody = "test body";


        Map<String, Object> data = new HashMap<>();
        data.put("type", FCMDataType.CLOSE_RECRUIT);
        data.put("data", "Hello World");


        //when
        Response res1 = firebaseCloudMessageService.sendMessageTo(groupToken, null, null, data);
        Response res2 = firebaseCloudMessageService.sendMessageTo(groupToken, null, testMsgBody + 2, null);
        Response res3 = firebaseCloudMessageService.sendMessageTo(groupToken, null, testMsgBody + 3, data);
        Response res4 = firebaseCloudMessageService.sendMessageTo(groupToken, testMsgTitle + 4, null, null);
        Response res5 = firebaseCloudMessageService.sendMessageTo(groupToken, testMsgTitle + 5, null, data);
        Response res6 = firebaseCloudMessageService.sendMessageTo(groupToken, testMsgTitle + 6, testMsgBody + 6, null);
        Response res7 = firebaseCloudMessageService.sendMessageTo(groupToken, testMsgTitle + 7, testMsgBody + 7, data);




        System.out.println("res1 : " + res1);
        System.out.println("res2 : " + res2);
        System.out.println("res3 : " + res3);
        System.out.println("res4 : " + res4);
        System.out.println("res5 : " + res5);
        System.out.println("res6 : " + res6);
        System.out.println("res7 : " + res7);


        //then
        assertTrue(res1.isSuccessful());
        assertTrue(res2.isSuccessful());
        assertTrue(res3.isSuccessful());
        assertTrue(res4.isSuccessful());
        assertTrue(res5.isSuccessful());
        assertTrue(res6.isSuccessful());
        assertTrue(res7.isSuccessful());




    }

    @Test
    void FCM_Push_그룹_메시지_테스트() {

        List<String> fcmToken = new LinkedList<>();
        String targetToken = "dTHVuLKXQ5KDR-2hoFH3_S:APA91bEVSgcPRUmq_vYHVosRjypQ0xxn2-D6gUnLlZvn2xxmMePvT15mYznhx-lmJ7iErgPg007vG6-bRoLWJFscauOHzYPp3lmlV4CmFtjk7KIxPcqLgkrmgeqnNCENlebxPZlNbwnM";
        fcmToken.add(targetToken);

        //  파이어베이스에 FCM 그룹 생성 요청 보내고 그룹 토큰 반환받는다. // throwable JSONException, IOException
        String fcmGroupToken = firebaseCloudMessageService.sendGroupRequest(
                FCMGroupRequest.Type.create,
                "DeliveryRoom",
                null,
                fcmToken
        );

        String testMsgTitle = "test group msg title";
        String testMsgBody = "test group msg body";

        Response groupRes = firebaseCloudMessageService.sendMessageTo(fcmGroupToken, testMsgTitle, testMsgBody, null);

        System.out.println("groupRes : " + groupRes);

        assertTrue(groupRes.isSuccessful());

    }
}
