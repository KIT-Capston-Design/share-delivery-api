package com.kitcd.share_delivery_api.dto.fcm;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Builder
@AllArgsConstructor
@Getter
public class FCMMessage {
    private boolean validate_only; // test 목적인가?
    private Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message{
        private String token; // device의 fcm 토큰
        private Notification notification;
        private Data data;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification{
        private String title;
        private String body;
        private String image;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Data{
        private FCMDataType type;
    }
}
