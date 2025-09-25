package com.finalproject.sos.domain.whiskey.entity;


import com.finalproject.sos.domain.common.entity.TimeStamped;
import com.finalproject.sos.domain.whiskey.dto.request.WhiskeyRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Whiskey extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long whiskeyId;

    @Column
    private String whiskeyName;

    @Column
    private String whiskeyBrand;

    @Column
    private String whiskeyUrl;

    @Column
    private String whiskeyType;

    @Column
    private Double whiskeyAbv;

    @Column
    private String whiskeyTaste;

    @Column
    private Integer whiskeyAge;

    @Builder
    public Whiskey (String whiskeyName, String whiskeyBrand, String whiskeyUrl, String whiskeyType, Double whiskeyAbv, String whiskeyTaste, Integer whiskeyAge){
        this.whiskeyName = whiskeyName;
        this.whiskeyBrand = whiskeyBrand;
        this.whiskeyUrl = whiskeyUrl;
        this.whiskeyAbv = whiskeyAbv;
        this.whiskeyType = whiskeyType;
        this.whiskeyAge = whiskeyAge;
        this.whiskeyTaste = whiskeyTaste;
    }

    public void updateWhiskey(WhiskeyRequestDto whiskeyRequestDto) {
        this.whiskeyName = (whiskeyRequestDto.getWhiskeyName() != null) ? whiskeyRequestDto.getWhiskeyName() : this.whiskeyName;
        this.whiskeyBrand = (whiskeyRequestDto.getWhiskeyBrand() != null) ? whiskeyRequestDto.getWhiskeyBrand() : this.whiskeyBrand;
        this.whiskeyUrl = (whiskeyRequestDto.getWhiskeyUrl() != null) ? whiskeyRequestDto.getWhiskeyUrl() : this.whiskeyUrl;
        this.whiskeyType = (whiskeyRequestDto.getWhiskeyType() != null) ? whiskeyRequestDto.getWhiskeyType() : this.whiskeyType;
        this.whiskeyAbv = (whiskeyRequestDto.getWhiskeyAbv() != null) ? whiskeyRequestDto.getWhiskeyAbv() : this.whiskeyAbv;
        this.whiskeyTaste = (whiskeyRequestDto.getWhiskeyTaste() != null) ? whiskeyRequestDto.getWhiskeyTaste() : this.whiskeyTaste;
        this.whiskeyAge = (whiskeyRequestDto.getWhiskeyAbv() != null) ? whiskeyRequestDto.getWhiskeyAge() : this.whiskeyAge;
    }
}
