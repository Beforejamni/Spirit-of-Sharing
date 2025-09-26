package com.finalproject.sos.domain.order.dto.response;

import com.finalproject.sos.domain.order.entity.Order;
import com.finalproject.sos.domain.order.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
public class CreatedOrderResponse {

    private String orderNum;

    private String whiskeyName;

    private Integer orderCnt;

    private BigDecimal orderPrice;

    private String paymentMethod;

    private OrderStatus status;

    private String koreanName;

    private String storeName;

    private String storeAddress;

    private LocalTime startTime;

    private LocalTime endTime;


    @Builder
    public CreatedOrderResponse (Order order){
        this.orderNum = order.getOrderNum();
        this.whiskeyName = order.getItem().getWhiskey().getWhiskeyName();
        this.orderCnt = order.getOrderCnt();
        this.orderPrice = order.getOrderPrice();
        this.paymentMethod = order.getPaymentMethod();
        this.status = order.getOrderStatus();
        this.koreanName = order.getMember().getKoreanName();
        this.storeName = order.getStore().getStoreName();
        this.storeAddress = order.getStore().getStoreAddress().getStoreAddress();
        this.startTime = order.getStore().getStartTime();
        this.endTime = order.getStore().getEndTime();
    }
}
