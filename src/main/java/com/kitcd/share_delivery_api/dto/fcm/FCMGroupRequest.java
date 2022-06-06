package com.kitcd.share_delivery_api.dto.fcm;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FCMGroupRequest {
    private Type operation;
    private String notification_key_name;
    private String notification_key;
    private List<String> registration_ids;

    public enum Type {
        create, add, remove
    }
}

