package com.kitcd.share_delivery_api.dto.fcm;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class FCMGroupRequest {
    private Type operation;
    private String notification_key_name;
    private List<String> registration_ids;

    public enum Type {
        create
    }
}

