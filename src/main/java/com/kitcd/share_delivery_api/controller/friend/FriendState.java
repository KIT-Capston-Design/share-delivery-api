package com.kitcd.share_delivery_api.controller.friend;

public enum FriendState {
    ACCEPTED, //친구 맺어진 상태
    RECEIVED_PENDING_REQUEST, //친구 요청 보낸 대기중인 상태
    SENT_PENDING_REQUEST , //친구 요청 받은 대기중인 상태
}
