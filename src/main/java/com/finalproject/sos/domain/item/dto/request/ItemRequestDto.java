package com.finalproject.sos.domain.item.dto.request;

import com.finalproject.sos.domain.item.entity.SnackType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ItemRequestDto {

    private SnackType snackType;

    private String cocktail;

    private Integer itemCnt;

    private BigDecimal itemPrice;

}
