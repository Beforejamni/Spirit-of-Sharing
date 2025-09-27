package com.finalproject.sos.domain.whiskey.dto.response;


import com.finalproject.sos.domain.whiskey.dto.request.WhiskeyRequestDto;
import com.finalproject.sos.domain.whiskey.entity.Whiskey;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WhiskeyResponseDto {

    private String whiskeyName;

    private String whiskeyBrand;

    private String whiskeyUrl;

    private String whiskeyType;

    private Double whiskeyAbv;

    private String whiskeyTaste;

    private Integer whiskeyAge;

    @Builder
    public WhiskeyResponseDto(Whiskey whiskey){
        this.whiskeyName = whiskey.getWhiskeyName();
        this.whiskeyBrand = whiskey.getWhiskeyBrand();
        this.whiskeyUrl = whiskey.getWhiskeyUrl();
        this.whiskeyType = whiskey.getWhiskeyType();
        this.whiskeyAbv = whiskey.getWhiskeyAbv();
        this.whiskeyTaste = whiskey.getWhiskeyTaste();
        this.whiskeyAge = whiskey.getWhiskeyAge();
    }
}
