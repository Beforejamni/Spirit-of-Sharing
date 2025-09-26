package com.finalproject.sos.domain.item.dto.response;


import com.finalproject.sos.domain.item.entity.Item;
import com.finalproject.sos.domain.item.entity.SnackType;
import com.finalproject.sos.domain.whiskey.entity.Whiskey;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemResponseDto {

    private String whiskeyName;

    private String whiskeyUrl;

    private String whiskeyBrand;

    private String whiskeyType;

    private Double whiskeyAbv;

    private Integer whiskeyAge;

    private String whiskeyTaste;

    private SnackType snack;

    private String cocktail;

    private Integer itemCnt;

    private BigDecimal itemPrice;

    @Builder
    public ItemResponseDto(Item item) {

        Whiskey whiskey = item.getWhiskey();

        this.whiskeyName = whiskey.getWhiskeyName();
        this.whiskeyUrl = whiskey.getWhiskeyUrl();
        this.whiskeyBrand = whiskey.getWhiskeyBrand();
        this.whiskeyType = whiskey.getWhiskeyType();
        this.whiskeyAbv = whiskey.getWhiskeyAbv();
        this.whiskeyAge = whiskey.getWhiskeyAge();
        this.whiskeyTaste = whiskey.getWhiskeyTaste();

        this.snack = (item.getSnackType() != null) ? item.getSnackType() : null;
        this.cocktail = (item.getCocktail() != null) ? item.getCocktail() : null;
        this.itemCnt = item.getItemCnt();
        this.itemPrice = item.getItemPrice();

    }

}
