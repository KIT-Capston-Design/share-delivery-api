package com.kitcd.share_delivery_api.domain.jpa.deliveryroom;

public enum DeliveryRoomState {
    WAITING, DEADLINE, ORDER, DELIVERY, REMIT, END // 대기중, 마감, 주문, 배달중, 송금 남아있음, 배달끝
}
