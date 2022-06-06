package com.kitcd.share_delivery_api.dto.fcm;

public enum FCMDataType {
    CLOSE_RECRUIT,
    EXIT_ROOM,
    COMPLETED_ORDER,
    COMMENT_PLUS, //대댓글알림
    POST_COMMENT, //댓글 -> 글작성자 알림
    ENTRY_ORDERS,
    ORDER_REJECTED,
    COMPLETE_DELIVERY

}
