package com.finalproject.sos.domain.store.dto.response;

import com.finalproject.sos.domain.item.entity.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleStoreResponseDto {

    //확인용
    private Long whiskeyId;

    private String storeName;

    private BigDecimal itemPrice;

    private Integer itemCnt;

    public SimpleStoreResponseDto(Item item) {

        this.whiskeyId = item.getWhiskey().getWhiskeyId();
        this.storeName = item.getStore().getStoreName();
        this.itemPrice = item.getItemPrice();
        this.itemCnt = item.getItemCnt();
    }
}
