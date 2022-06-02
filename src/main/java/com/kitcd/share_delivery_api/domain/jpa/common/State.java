package com.kitcd.share_delivery_api.domain.jpa.common;

public enum State {
    DEFAULT,
    NORMAL,
    PENDING, //대기 중
    REJECTED, // 거절됨
    ACCEPTED, // 승인됨
    CANCELLED, // 퇴장함
}
