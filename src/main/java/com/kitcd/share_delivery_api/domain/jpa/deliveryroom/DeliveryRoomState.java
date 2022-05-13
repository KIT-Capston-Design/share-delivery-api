package com.kitcd.share_delivery_api.domain.jpa.deliveryroom;

public enum DeliveryRoomState {
    OPEN,
    WAITING_PAYMENT,
    WAITING_DELIVERY,
    WAITING_REMITTANCE,
    COMPLETED
}
