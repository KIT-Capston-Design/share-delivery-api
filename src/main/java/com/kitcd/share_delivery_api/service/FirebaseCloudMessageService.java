package com.kitcd.share_delivery_api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kitcd.share_delivery_api.dto.fcm.FCMDataType;
import com.kitcd.share_delivery_api.dto.fcm.FCMGroupRequest;
import okhttp3.Response;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface FirebaseCloudMessageService {
    //그룹 생성/입장/퇴장/삭제(모두퇴장)
    //성공 시 notification_key 반환
    //실패 시 null 반환
    String sendGroupRequest(FCMGroupRequest.Type requestType, String groupTokenName, String groupKey, List<String> userTokens);

    // 메시지 생성 & 발송
    Response sendMessageTo(String targetToken, String title, String body, FCMDataType type);
}
