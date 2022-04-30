package com.kitcd.share_delivery_api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.kitcd.share_delivery_api.dto.fcm.FCMMessage;
import com.kitcd.share_delivery_api.service.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class FirebaseCloudMessageServiceImpl implements FirebaseCloudMessageService {

    private final String apiUrl = "https://fcm.googleapis.com/v1/projects/812458932745/messages:send";

    private final GoogleCredentials googleCredentials; //구글 자격증명 클래스.. 이 친구 통해서 fcm access token 얻을 수 있다.

    private final ObjectMapper objectMapper = new ObjectMapper();

    public FirebaseCloudMessageServiceImpl() throws IOException {
        final String firebaseConfigPath = "firebase/fcm-private-key.json"; // 프로젝트 내부 key path

        googleCredentials = GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                        .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
    }

    public Response sendMessageTo(String targetToken, String title, String body) throws IOException {

        String message = makeMessage(targetToken, title, body);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(apiUrl)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        return client.newCall(request).execute();
    }


    private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
        FCMMessage fcmMessage = FCMMessage.builder()
                .message(
                        FCMMessage.Message.builder()
                                .token(targetToken)
                                .notification(
                                        FCMMessage.Notification.builder()
                                                .title(title)
                                                .body(body)
                                                .image(null)
                                                .build()
                                )
                                .build()
                )
                .validate_only(false)
                .build();

        return objectMapper.writeValueAsString(fcmMessage);
    }


    private String getAccessToken() throws IOException {
        // credentials가 만료되었거나 만료임박의 경우 refresh
        googleCredentials.refreshIfExpired();

        return googleCredentials.getAccessToken().getTokenValue();
    }
}
