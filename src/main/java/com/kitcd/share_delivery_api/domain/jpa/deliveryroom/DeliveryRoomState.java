package com.kitcd.share_delivery_api.domain.jpa.deliveryroom;

public enum DeliveryRoomState {
    OPEN, // 모집글 생성 시
    WAITING_PAYMENT, // 인원 모집 마감 시
    WAITING_DELIVERY, // 주도자가 주문서 등록 시
    WAITING_REMITTANCE, // 주도자가 빨간버튼 누를 시
    COMPLETED, // 송금 전부 끝날 시
    DELETED // OPEN, WAITING_PAYMENT 상태에서 모집글 삭제 시
}
