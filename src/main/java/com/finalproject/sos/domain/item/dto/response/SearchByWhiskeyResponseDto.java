package com.finalproject.sos.domain.item.dto.response;


import com.finalproject.sos.domain.item.entity.Item;
import com.finalproject.sos.domain.store.entity.StoreStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchByWhiskeyResponseDto {

    private String whiskeyName;

    private String whiskeyUrl;

    private Integer itemCnt;

    private BigDecimal itemPrice;

    private String storeName;

    private StoreStatus status;


    public SearchByWhiskeyResponseDto(Item item) {

        this.whiskeyName = item.getWhiskey().getWhiskeyName();
        this.whiskeyUrl = item.getWhiskey().getWhiskeyUrl();
        this.itemCnt = item.getItemCnt();
        this.itemPrice = item.getItemPrice();
        this.storeName = item.getStore().getStoreName();
        this.status = item.getStore().getStoreStatus();
    }
}
