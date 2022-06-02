package com.kitcd.share_delivery_api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.kitcd.share_delivery_api.dto.fcm.FCMDataType;
import com.kitcd.share_delivery_api.dto.fcm.FCMGroupRequest;
import com.kitcd.share_delivery_api.dto.fcm.FCMMessage;
import com.kitcd.share_delivery_api.service.FirebaseCloudMessageService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class FirebaseCloudMessageServiceImpl implements FirebaseCloudMessageService {

    @Value("${fcm.v1-url}") private String V1_URL;
    @Value("${fcm.legacy-url}") private String LEGACY_URL;
    @Value("${fcm.project-id}") private String PROJECT_ID;
    @Value("${fcm.legacy-access-key}") private String LEGACY_ACCESS_KEY;

    private final GoogleCredentials googleCredentials; //구글 자격증명 클래스.. 이 친구 통해서 fcm access token 얻을 수 있다.

    private final ObjectMapper objectMapper = new ObjectMapper();

    public FirebaseCloudMessageServiceImpl() throws IOException {
        final String firebaseConfigPath = "firebase/fcm-private-key.json"; // 프로젝트 내부 key path

        googleCredentials = GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                        .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
    }


    //그룹 생성/입장/퇴장/삭제(모두퇴장)
    //성공 시 notification_key 반환
    //실패 시 null 반환
    @Override
    public String sendGroupRequest(FCMGroupRequest.Type requestType, String groupTokenName, String groupKey, List<String> userTokens) {

        try {
            //요청 데이터 생성
            String requestData = makeFCMGroupRequest(requestType, groupTokenName, groupKey, userTokens);

            //전송
            Response response = legacyForward(requestData);

            //실패시 null 반환
            if (!response.isSuccessful()) {
                log.error(response.toString());
                return null;
            }

            String body = Objects.requireNonNull(response.body()).toString();
            JSONObject jsonBody = new JSONObject(body);

            //성공 시 notification_key 반환
            return jsonBody.getString("notification_key");

        }catch(IOException | JSONException e){
            log.error(e.getMessage());
            return null;
        }

    }

    // 알림 메시지 생성 & 발송
    @Override
    public Response sendMessageTo(String targetToken, String title, String body, Map<String, Object> data) {
        try {
            String message = makeMessage(targetToken, title, body, data);
            return v1Forward(message);

        } catch(IOException ioe){
            log.error(ioe.getMessage());
            return null;
        }
    }


    private String makeFCMGroupRequest(FCMGroupRequest.Type type, String groupKeyName, String groupKey, List<String> userTokens) throws JsonProcessingException {

        return objectMapper.writeValueAsString(FCMGroupRequest.builder()
                .operation(type)
                .notification_key_name(groupKeyName)
                .notification_key(groupKey)
                .registration_ids(userTokens)
                .build());

    }

    public String makeMessage(String targetToken, String title, String body, Map<String, Object> data) throws JsonProcessingException {
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
                                .data(data)
                                .build()
                )
                .validate_only(false)
                .build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    private Response legacyForward(String message) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(LEGACY_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, LEGACY_ACCESS_KEY)
                .addHeader("project_id", PROJECT_ID)
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        return client.newCall(request).execute();
    }

    private Response v1Forward(String message) throws IOException{

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(V1_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        return client.newCall(request).execute();

    }

    private String getAccessToken() throws IOException {
        // credentials가 만료되었거나 만료임박의 경우 refresh
        googleCredentials.refreshIfExpired();

        return googleCredentials.getAccessToken().getTokenValue();
    }
}
