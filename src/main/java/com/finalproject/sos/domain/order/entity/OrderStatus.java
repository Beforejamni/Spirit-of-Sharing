package com.finalproject.sos.domain.order.entity;

public enum OrderStatus {
    WAITING_ORDER, //주문 대기
    COMPLETED_ORDER, // 주문 완료
    CANCELED_ORDER, // 주문 취소
    IN_DELIVERY, // 배송 중
    PICKUPABLE, // 픽업 가능
    COMPLETED_PICKUP // 픽업 완료

}
