package com.kitcd.share_delivery_api.domain.jpa.entryorder;

public enum EntryOrderType {
    PENDING, // 응답 대기 중
    REJECTED, // 거절됨
    ACCEPTED // 승인됨
}
