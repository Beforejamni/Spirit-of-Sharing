package com.finalproject.sos.domain.whiskey.dto.request;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class WhiskeyRequestDto {

    private String whiskeyName;

    private String whiskeyBrand;

    private String whiskeyUrl;

    private String whiskeyType;

    private Double whiskeyAbv;

    private String whiskeyTaste;

    private Integer whiskeyAge;
}
